package com.example.rockclass.controller;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.entity.*;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.service.RoundService;
import com.example.rockclass.service.SeminarService;
import com.example.rockclass.service.TeamService;
import com.example.rockclass.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class RoundController {

    @Autowired
    private RoundService roundService;

    @Autowired
    private TeamService teamService;
    @Autowired
    private SeminarService seminarService;
    @GetMapping("round/{roundId}/seminar")
    public void getRoundSeminar(@PathVariable("roundId") Long roundId, HttpServletResponse httpServletResponse) throws IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        List<Seminar> seminars= seminarService.selectSeminarByRoundId(roundId);
        if(seminars.size()==0||seminars==null)
            httpServletResponse.setStatus(404);
        else {
            httpServletResponse.setStatus(200);
            List<RoundSeminarVo>roundSeminarVos= new ArrayList<RoundSeminarVo>();
            for (int i=0;i<seminars.size();i++)
            {   RoundSeminarVo roundSeminarVo = new RoundSeminarVo();
                roundSeminarVo.setId(seminars.get(i).getId());
                roundSeminarVo.setOrder(seminars.get(i).getSeminarSerial());
                roundSeminarVo.setTopic(seminars.get(i).getSeminarName());
                roundSeminarVos.add(roundSeminarVo);
            }
            String json = JSON.toJSONString(roundSeminarVos);
            httpServletResponse.getWriter().write(json);
        }
    }
    @PutMapping("/round/{roundId}/roundscores")
    public  void updateRoundScores(@PathVariable("roundId") Long roundId , @RequestBody RoundScoresVo roundScoresVo, HttpServletResponse httpServletResponse) throws  IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        for (RoundScoreVo roundScoreVo:roundScoresVo.getRoundScoreVos()) {
            RoundScore roundScore = new RoundScore();
            roundScore.setTeam(teamService.selectTeamByTeamId(roundScoreVo.getTeamId()));
            roundScore.setRound(roundService.selectRoundByRoundId(roundId));
            roundScore.setPresentationScore(roundScoreVo.getPreScore());
            roundScore.setQuestionScore(roundScoreVo.getQuestionScore());
            roundScore.setReportScore(roundScoreVo.getReportScore());
            roundService.updateRoundScoreByRoundScore(roundScore);
        }
        httpServletResponse.setStatus(200);
    }

    @PutMapping("round/{roundId}")
    public void updateRound(@PathVariable("roundId") Long roundId, @RequestBody RoundInfoVo roundInfoVo, HttpServletResponse httpServletResponse) throws IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        int status =  roundService.updateRoundByRoundId(roundId,roundInfoVo);
        httpServletResponse.setStatus(status);
    }

    @GetMapping("round/{roundId}")
    public void getRound(@PathVariable("roundId") Long roundId, HttpServletResponse httpServletResponse) throws IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Round round=roundService.selectRoundByRoundId(roundId);
        if (round==null) httpServletResponse.setStatus(404);
        else {
            List<KlassRound> klassRounds = roundService.selectKlassRoundByRoundId(roundId);
            List<ClassRoundVo> classRoundVos = new ArrayList<ClassRoundVo>();
            for(int i=0;i<klassRounds.size();i++)
            {
                ClassRoundVo classRoundVo = new ClassRoundVo();
                classRoundVo.setClassSerial(klassRounds.get(i).getKlass().getKlassSerial());
                classRoundVo.setEnrollNum(klassRounds.get(i).getEnrollNumber());
                classRoundVo.setId(klassRounds.get(i).getKlass().getId());
                classRoundVos.add(classRoundVo);
            }
            Map map=new HashMap();
            map.put("id",roundId);
            map.put("order",round.getRoundSerial());
            map.put("calculatePreType",roundService.typeByteToString(round.getPresentationScoreMethod()));
            map.put("calculateQueType",roundService.typeByteToString(round.getQuestionScoreMethod()));
            map.put("calculateRepType",roundService.typeByteToString(round.getReportScoreMethod()));
            map.put("classRound",classRoundVos);

            httpServletResponse.setStatus(200);
            String json = JSON.toJSONString(map);
            httpServletResponse.getWriter().write(json);
        }
    }


    @PostMapping("/round")
    public  void  createRound(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, CourseNotFoundException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Long id=roundService.creatRound(Long.parseLong(httpServletRequest.getParameter("courseId")));
        httpServletResponse.setStatus(200);
        String json = JSON.toJSONString(id);
        httpServletResponse.getWriter().write(json);
    }

    @GetMapping("/round/{roundId}/roundscore")
    public  void getRoundScore(@PathVariable("roundId") Long roundId ,HttpServletResponse httpServletResponse) throws  IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        List<RoundScore>roundScores= roundService.selectRoundScoreByRoundId(roundId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i=0;i<roundScores.size();i++)
        {
            Team team = roundScores.get(i).getTeam();
            Map map = new HashMap(2);
            Map teamMap = new HashMap(2);
            teamMap.put("id",team.getId());
            teamMap.put("name",team.getKlassSerial().toString()+"-"+team.getTeamSerial().toString());
            map.put("team",teamMap);
            map.put("preScpre",roundScores.get(i).getPresentationScore());
            map.put("reportScore",roundScores.get(i).getReportScore());
            map.put("questionScore",roundScores.get(i).getQuestionScore());
            map.put("totalScore",roundScores.get(i).getTotalScore());
            list.add(map);
        }
        String json =JSON.toJSONString(list);
        httpServletResponse.getWriter().write(json);
        httpServletResponse.setStatus(200);
    }
    @GetMapping("/round/{roundId}/team/{teamId}/roundscore")
    public  void getTeamRoundScore(@PathVariable("roundId") Long roundId,@PathVariable("teamId") Long teamId ,HttpServletResponse httpServletResponse) throws  IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        RoundScore roundScore= roundService.selectRoundScoreByRoundIdAndTeamId(roundId,teamId);
        Team team = roundScore.getTeam();
        Map map = new HashMap(2);
        Map teamMap = new HashMap(2);
        teamMap.put("id",team.getId());
        teamMap.put("name",team.getKlassSerial().toString()+"-"+team.getTeamSerial().toString());
        map.put("team",teamMap);
        map.put("preScore",roundScore.getPresentationScore());
        map.put("reportScore",roundScore.getReportScore());
        map.put("questionScore",roundScore.getQuestionScore());
        map.put("totalScore",roundScore.getTotalScore());
        String json =JSON.toJSONString(map);
        httpServletResponse.getWriter().write(json);
        httpServletResponse.setStatus(200);
    }



    @PutMapping("/round/{roundId}/team/{teamId}/roundscore")
    public  void updateTeamRoundScore(@PathVariable("roundId") Long roundId, @PathVariable("teamId") Long teamId , @RequestBody RoundScoreVo roundScoreVo, HttpServletResponse httpServletResponse) throws  IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        RoundScore roundScore =  new RoundScore();
        roundScore.setTeam(teamService.selectTeamByTeamId(teamId));
        roundScore.setRound(roundService.selectRoundByRoundId(roundId));
        roundScore.setPresentationScore(roundScoreVo.getPreScore());
        roundScore.setQuestionScore(roundScoreVo.getQuestionScore());
        roundScore.setReportScore(roundScoreVo.getReportScore());
        int status=roundService.updateRoundScoreByRoundScore(roundScore);
        httpServletResponse.setStatus(status);
    }


}
