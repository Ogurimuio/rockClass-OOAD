package com.example.rockclass.service;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.dao.*;
import com.example.rockclass.entity.*;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.vo.RoundInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoundService {

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private KlassRoundDao klassRoundDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private SeminarScoreDao seminarScoreDao;
    @Autowired
    private SeminarService seminarService;
    @Autowired
    private SeminarDao seminarDao;
    @Autowired
    private  KlassDao classDao;
    public int updateRoundByRoundId(Long roundId, RoundInfoVo roundInfoVo)
    {
        Round round=roundDao.selectByPrimaryKey(roundId);
        if (round == null) return 400;
        round.setPresentationScoreMethod(typeStringToByte(roundInfoVo.getCalculatePreType()));
        round.setQuestionScoreMethod(typeStringToByte(roundInfoVo.getCalculateQueType()));
        round.setReportScoreMethod(typeStringToByte(roundInfoVo.getCalculateRepType()));
        for (int i=0;i<roundInfoVo.getClassRound().size();i++) {
            KlassRound klassRound = klassRoundDao.selectByPrimaryKey(roundInfoVo.getClassRound().get(i).getId(), roundId);
            klassRound.setEnrollNumber(roundInfoVo.getClassRound().get(i).getEnrollNum());
            klassRoundDao.updateByPrimaryKey(klassRound);
        }
        roundDao.updateByPrimaryKey(round);
        return 204;
    }

    public Round selectRoundByRoundId(Long roundId){return roundDao.selectByPrimaryKey(roundId);}

    public List<KlassRound> selectKlassRoundByRoundId(Long roundId){return klassRoundDao.selectByRoundId(roundId);}

    public  List<RoundScore> selectRoundScoreByRoundId(Long roundId){return roundScoreDao.selectByRoundId(roundId);}
    public  RoundScore selectRoundScoreByRoundIdAndTeamId(Long roundId,Long teamId){return roundScoreDao.selectByPrimaryKey(roundId,teamId);}

    public int updateRoundScoreByRoundScore(RoundScore roundScore){
        BigDecimal presentationPercentage= new BigDecimal(roundScore.getTeam().getCourse().getPresentationPercentage()/100.0);
        BigDecimal reportPercentage= new BigDecimal(roundScore.getTeam().getCourse().getReportPercentage()/100.0);
        BigDecimal questionPercentage= new BigDecimal(roundScore.getTeam().getCourse().getQuestionPercentage()/100.0);
        BigDecimal totalScore = presentationPercentage.multiply(roundScore.getPresentationScore());
        totalScore=totalScore.add(reportPercentage.multiply(roundScore.getReportScore()));
        totalScore=totalScore.add(questionPercentage.multiply(roundScore.getQuestionScore()));
        roundScore.setTotalScore(totalScore.setScale(2,BigDecimal.ROUND_HALF_UP));
        roundScoreDao.updateByPrimaryKey(roundScore);
        return 200;

    }
    public BigDecimal calculateTotalScore(BigDecimal preScore, BigDecimal questionScore, BigDecimal reportScore, Course course){
        BigDecimal presentationPercentage= new BigDecimal(course.getPresentationPercentage()/100.0);
        BigDecimal reportPercentage= new BigDecimal(course.getReportPercentage()/100.0);
        BigDecimal questionPercentage= new BigDecimal(course.getQuestionPercentage()/100.0);
        BigDecimal totalScore = presentationPercentage.multiply(preScore);
        totalScore=totalScore.add(reportPercentage.multiply(reportScore));
        totalScore=totalScore.add(questionPercentage.multiply(questionScore));
        return totalScore.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public long creatRound(Long courseId)throws CourseNotFoundException {
        Round round = new Round();
        Course course = courseDao.selectCourseById(courseId);
        round.setCourse(course);
        int num = roundDao.selectRoundByCourseId(courseId).size()+1;
        round.setRoundSerial(Byte.parseByte(Integer.toString(num)));
        Byte moren = 0;
        round.setReportScoreMethod(moren);
        round.setQuestionScoreMethod(moren);
        round.setPresentationScoreMethod(moren);
        String json = JSON.toJSONString(round);

        //System.out.println(json);
        Long id=roundDao.insert(round);

        List<Klass> klasses = classDao.selectKlassByCourseId(courseId);
        for (Klass klass:klasses)
        {
            KlassRound klassRound = new KlassRound();
            klassRound.setEnrollNumber(Byte.parseByte("1"));
            klassRound.setKlass(klass);
            klassRound.setRound(round);
            klassRoundDao.insert(klassRound);
        }
        return id;
    }
    public Byte typeStringToByte(String typeString)
    {
        if(typeString.equals("最高分")) return 0;
        if(typeString.equals("平均分")) return 1;
        return 2;
    }

    public String typeByteToString(Byte typeByte)
    {
        if(typeByte==0) return "最高分";
        if(typeByte==1) return "平均分";
        return "最低分";
    }

    public BigDecimal calculateScoreByListScoreAndType(List<BigDecimal> scores,Byte type)
    {

        BigDecimal result = new BigDecimal(0);
        if (type.equals(Byte.parseByte("0")))
        {
            for (BigDecimal score:scores)
            {

                if (score.compareTo(result)>0) result=new BigDecimal(score.toString());
            }
        }
        else if (type.equals(Byte.parseByte("2"))) {
            for (BigDecimal score : scores) {
                if (score.compareTo(result) > 0) result = new BigDecimal(score.toString());
            }
        }
        else if (type.equals(Byte.parseByte("1"))){
            for (BigDecimal score : scores) {
                result.add(score);
            }
            BigDecimal n=new BigDecimal(scores.size());
            result = new BigDecimal(result.divide(n,2,BigDecimal.ROUND_HALF_UP).toString());
        }
        return result;
    }

    public void calculateNewRoundScore(Round round,Team team)
    {

        RoundScore roundScore = roundScoreDao.selectByPrimaryKey(round.getId(),team.getId());
        List<Seminar> seminars = seminarDao.selectSeminarByRoundId(round.getId());
        if (seminars!=null) {
            List<SeminarScore> seminarScores = new ArrayList<SeminarScore>();
            for (Seminar seminar : seminars) {
                if (seminarService.selectSeminarScoreBySeminarIdAndTeamId(seminar.getId(), team.getId())!=null)
                seminarScores.add(seminarService.selectSeminarScoreBySeminarIdAndTeamId(seminar.getId(), team.getId()));
            }
            List<BigDecimal> preScores = new ArrayList<>();
            List<BigDecimal> reportScores = new ArrayList<>();
            List<BigDecimal> questionScores = new ArrayList<>();
            if (seminarScores != null) {
                for (SeminarScore seminarScore : seminarScores) {
                    if (seminarScore.getPresentationScore()!=null)
                    preScores.add(seminarScore.getPresentationScore());
                    else preScores.add(BigDecimal.ZERO);
                    if (seminarScore.getReportScore()!=null)
                    reportScores.add(seminarScore.getReportScore());
                    else seminarScore.setReportScore(BigDecimal.ZERO);
                    if (seminarScore.getQuestionScore()!=null)
                        questionScores.add(seminarScore.getQuestionScore());
                    else seminarScore.setQuestionScore(BigDecimal.ZERO);

                }
                if (preScores.isEmpty()) roundScore.setPresentationScore(BigDecimal.ZERO);
                else
                    roundScore.setPresentationScore(calculateScoreByListScoreAndType(preScores, round.getPresentationScoreMethod()));
                if (reportScores.isEmpty()) roundScore.setReportScore(BigDecimal.ZERO);
                else
                    roundScore.setReportScore(calculateScoreByListScoreAndType(preScores, round.getReportScoreMethod()));
                if (questionScores.isEmpty()) roundScore.setQuestionScore(BigDecimal.ZERO);
                else {
                    for (SeminarScore seminarScore : seminarScores) {
                        if (round.getQuestionScoreMethod().equals(Byte.parseByte("1"))) {
                            List<Question> questions = questionDao.selectByKlassSeminarIdAndTeamId(seminarScore.getKlassSeminar().getId(), team.getId());
                            BigDecimal all = new BigDecimal(0);
                            int n = 0;
                            for (Question question : questions) {
                                if (question.getIsSelected().equals(Byte.parseByte("0"))) continue;
                                all.add(question.getScore());
                                n++;
                            }
                            BigDecimal n1 = new BigDecimal(n);
                            if (all.compareTo(BigDecimal.ZERO) == 0) seminarScore.setQuestionScore(BigDecimal.ZERO);
                            else seminarScore.setQuestionScore(all.divide(n1, 2, BigDecimal.ROUND_HALF_DOWN));
                        } else
                            roundScore.setQuestionScore(calculateScoreByListScoreAndType(preScores, round.getQuestionScoreMethod()));
                    }
                }
                roundScore.setTotalScore(calculateTotalScore(roundScore.getPresentationScore(), roundScore.getQuestionScore(), roundScore.getReportScore(), round.getCourse()));
                roundScoreDao.updateByPrimaryKey(roundScore);
            }
        }
    }

    public int putQuestionScore(Long questionId, BigDecimal questionScore){
        Question question = questionDao.selectByPrimaryKey(questionId);
        if (question==null) return 404;
        question.setScore(questionScore);
        questionDao.updateByPrimaryKey(question);
        Round round = question.getKlassSeminar().getSeminar().getRound();
        BigDecimal zero = new BigDecimal(0);
        SeminarScore seminarScore = seminarScoreDao.selectByPrimaryKey(question.getKlassSeminar().getId(),question.getTeam().getId());
        if (seminarScore==null) {seminarScore.setQuestionScore(questionScore);seminarScoreDao.insert(seminarScore);}
        else {
            if (seminarScore.getQuestionScore() == null) seminarScore.setQuestionScore(questionScore);
            else {
                int i = questionScore.compareTo(seminarScore.getQuestionScore());
                if (round.getQuestionScoreMethod().equals(Byte.parseByte("0"))) {
                    if (i > 0) seminarScore.setQuestionScore(questionScore);
                } else if (round.getQuestionScoreMethod().equals(Byte.parseByte("1"))) {
                    List<Question> questions = questionDao.selectByKlassSeminarIdAndTeamId(question.getKlassSeminar().getId(), question.getTeam().getId());
                    BigDecimal all = new BigDecimal(0);
                    int n = 0;
                    for (Question question1 : questions) {
                        if (question.getIsSelected().equals(Byte.parseByte("0"))) continue;
                        all.add(question1.getScore());
                        n++;
                    }
                    BigDecimal n1 = new BigDecimal(n);
                    if (all.compareTo(zero) == 0) seminarScore.setQuestionScore(zero);
                    else seminarScore.setQuestionScore(all.divide(n1, 2, BigDecimal.ROUND_HALF_DOWN));
                } else {
                    if (i < 0) seminarScore.setQuestionScore(questionScore);
                }
            }
            seminarScoreDao.updateByPrimaryKey(seminarScore);
        }
        return 200;
    }

    public List<Round> selectRoundByCourseId(Long courseId){return roundDao.selectRoundByCourseId(courseId);}


}
