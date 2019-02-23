# 概要设计说明书

**2019.01.06**



**目录**

[1. 引言	3](#_Toc531299851)

[1.1 编写目的	3](#_Toc531299852)

[1.2 项目背景	3](#_Toc531299853)

[1.3 定义	3](#_Toc531299854)

[1.4 参考资料	3](#_Toc531299855)

[2. 任务概述	5](#_Toc531299856)

[2.1 目标	5](#_Toc531299857)

[2.2 运行环境	5](#_Toc531299858)

[2.3 需求概述	5](#_Toc531299859)

[2.4 条件与限制	6](#_Toc531299860)

[3. 总体设计	7](#_Toc531299861)

[3.1 处理流程	7](#_Toc531299862)

[3.2 总体结构和模块外部设计	9](#_Toc531299863)

[3.3 功能分配	11](#_Toc531299864)

[4. 接口设计	12](#_Toc531299865)

[4.1 外部接口	12](#_Toc531299866)

[4.2 内部接口	12](#_Toc531299867)

[5. 数据结构设计	13](#_Toc531299868)

[5.1 逻辑结构设计	13](#_Toc531299869)

[5.2 物理结构设计	15](#_Toc531299870)

[5.3 数据结构与程序的关系	16](#_Toc531299871)

[6. 运行设计	17](#_Toc531299872)

[6.1 运行模块的组合	17](#_Toc531299873)

[6.2 运行控制	19](#_Toc531299874)

[6.3 运行时间	21](#_Toc531299875)

[7. 出错处理设计	22](#_Toc531299876)

[7.1 出错输出信息	22](#_Toc531299877)

[7.2 出错处理对策	22](#_Toc531299878)

[8. 安全保密设计	23](#_Toc531299879)

[9. 维护设计	23](#_Toc531299880)

 

 

 



# **1.** **引言**

## **1.1** **编写目的**

本文档主要描述的是课堂管理系统的总体设计、接口设计、数据结构设计、运行设计、出错处理设计。主要阅读者是开发系统的程序员。

## **1.2** **项目背景**

近几年来，随着教学课堂改革的不断深入，出现越来越多的课堂教学模式，课堂管理也越来越复杂多样，给老师的课堂教学管理带来了极大挑战！然而现有的课程管理系统或者忽略了教师课堂管理的需求，或者存在功能不健全、不完善的问题，不能帮助教师更好地管理课堂，帮助学生更好地参与课堂。

为了使课堂的管理更具有条理性，本课堂管理系统针对翻转课堂的教学模式，特别是对不同课程之间的共享分组、翻转课堂的讨论课的需求进行设计。

## **1.3** **定义**

暂无。

## **1.4** **参考资料**

罗杰S.普莱斯曼，布鲁斯R.马克西姆.《软件工程——实践者的研究方法》.第八版.北京.机械工业出版社.2016年

徐明华.《ASP .NET基础与案例开发详解》.第一版.北京.清华大学出版社.2014年

 



# **2.** **任务概述**

## **2.1** **目标**

本项目是为了改变不同课堂教学模式带来的教学管理问题，帮助教师更有条理地管理课堂，以提供更全面便捷的管理为目标的课堂管理系统。实现老师创建课程、班级、共享分组及实现讨论课相关内容，如课堂展示、课堂提问、报告评分、分数统计的功能，减轻老师的工作量；学生查看其课程、班级信息、查看和报名讨论课、查看成绩等。

## **2.2** **运行环境**

Windows操作系统，IE、Chrome等电脑端浏览器，安卓、苹果系统手机端浏览器。

## **2.3** **需求概述**

为了使讨论课的课堂能够高效运转，减轻老师和学生们的负担，简化人工繁琐又不必要的工作，方便师生进行教学活动，我们开发了这套课堂管理系统，开发系统的主要解决的问题如下：老师在系统上发布课程和讨论课以及创建班级，学生通过系统查看讨论课信息。通过系统就能进行信息统计与计算。教师可以提前对课堂内容进行设置，方便学生提前获知课堂内容，打破了师生交流堵塞状态。用系统上创建分组的形式替代传统的课堂中现场按座位分配小组方式。系统发起提问替代举手提问老师挑选的机制，更加能保证随机性和公平。此外，学生能通过系统提前组队，选择话题报名发言，查看自己的成绩分组等信息等。传统的纸张管理数据，容易遭到信息的修改，通过系统管理数据，对于角色的权限不同，访问的数据也不同，提高数据安全性。

## **2.4** **条件与限制**

编程语言：Java、HTML、JavaScript等。

通信协议：TCP/IP。

使用Windows操作系统，mySQL数据库。

 



# **3.** **总体设计**

## **3.1** **处理流程**

**3.1.1** **总活动图**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/e8zFjA5W3u9rMebljRvNb4bpF5ZPk9Diy6eWKEyJ7JQ!/b/dDcBAAAAAAAA&bo=QwQZAwAAAAADB38!&rf=viewer_4) 



 

**3.1.2** **分组、共享讨论课、共享分组活动图**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/03*pqGnyOX5siYFZtV1Bg1q1RsNMa9MXFafTZuKBcF0!/b/dFMBAAAAAAAA&bo=0AKcAQAAAAADF30!&rf=viewer_4) 

**3.1.3** **讨论课活动图**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/kmRjY8jvu3W2ytlGPT.vH1KnOsWa7p0vsRsAqAkP4Gk!/b/dMAAAAAAAAAA&bo=0AIIAgAAAAADF.o!&rf=viewer_4) 



## **3.2** **总体结构和模块外部设计**

**3.2.1** **逻辑结构**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/kQ3*dOkhUp94S7piafNgLg.y054Tv4TLTyjwwgi.s8k!/b/dFQBAAAAAAAA&bo=FgGLAQAAAAADF68!&rf=viewer_4) 

**3.2.2** **数据结构**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/B6qve3U4fFSlBxDp6KZZ4BEJGqywYIQTiS.PoKBWNZE!/b/dFMBAAAAAAAA&bo=TAMlAgAAAAADJ2o!&rf=viewer_4) 

**3.2.3** **物理结构**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/GGG.8FiNw3Lz6ErBR3w7sFVvoWnw1XJTVfWGx4Fy6m8!/b/dL8AAAAAAAAA&bo=7wBgAQAAAAADF7w!&rf=viewer_4) 

**3.2.4** **功能结构**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/X*DoL4bhByLI.vRtGwPOZ2mStfEgAIl.1pjSySwOt.Q!/b/dL8AAAAAAAAA&bo=0AIZAgAAAAADF*s!&rf=viewer_4) 



**3.2.5** **模块外部设计**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/ud*dydnoeUNv2nWBLwqTW*QyRHRxRJQGM0Z5vnEQzIQ!/b/dLwAAAAAAAAA&bo=wQHRAQAAAAADFyI!&rf=viewer_4) 

## **3.3** **功能分配**

| 模块        | 功能                                                         |
| ----------- | ------------------------------------------------------------ |
| **Account** | Teacher或者Student登录系统，激活帐号、修改个人信息等         |
| **Course**  | 教师创建的课程信息管理，以及学生登录查看到与自己相关的课程的信息 |
| **Classx**  | 教师在课程下创建的班级信息管理及相关活动，如导入学生名单和分组 |
| **Seminar** | 教师在课程下创建的讨论课信息管理，包括控制讨论课流程和上传下载资料报告 |
| **Score**   | 管理讨论课各小组成绩以及学生的成绩                           |

 

# **4.** **接口设计**

## **4.1** **外部接口**

\1. 用户界面

要求能够进行简单的用户交互，处理用户的输入信息，并在相应的页面显示用户所查询的信息。如错误信息的反馈，当用户登录失败时应提示具体错误原因。功能方面，教师端查看学生分组情况、学生端查看讨论课、查看成绩等，均需在页面显示对应信息。

\2. 其他外部接口

暂无。

## **4.2** **内部接口**

\1. Account、Course、Classx、Seminar模块需要数据库中保存的账号及课程信息。

\2. Score模块需要数据库中保存的成绩信息。



# **5.** **数据结构设计**

## **5.1** **逻辑结构设计**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/GmwUOfwi9l4RQTvIFFopTPnIdikzz0OTfE94HKC9w00!/b/dL8AAAAAAAAA&bo=lgJyAQAAAAADF9U!&rf=viewer_4)

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/H87hFHnDreH*7GE5k.jgFNA3QRNimJf*kDHwj1jo*ks!/b/dFIBAAAAAAAA&bo=rQQdAwAAAAADF4U!&rf=viewer_4)

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/BpVLWByb*QvgNT5O8ZfLZRuJoeDxp8b*O.inb6*9c.g!/b/dLgAAAAAAAAA&bo=GAUjAgAAAAADFw4!&rf=viewer_4)

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/Hfs0J7GTyjAFejkm4GeufgrvxtARcJ5pNb5217BEWsg!/b/dL4AAAAAAAAA&bo=2ARKAgAAAAADF6Y!&rf=viewer_4)

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/S1WuKW7Lm2JUW6qfbsuq7sMAOSwhMULCmAXH08Xb8KE!/b/dL4AAAAAAAAA&bo=AwTbAQAAAAADF.8!&rf=viewer_4)

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/.wqAWNE4EGWuEXZsPaRypGuYjlr0xmtSnbPSBhC4yq4!/b/dDUBAAAAAAAA&bo=bwTkAQAAAAADF7w!&rf=viewer_4)

## **5.2** **物理结构设计**

![img](http://m.qpic.cn/psb?/V13hFC1G0CiYu2/3tB.dUmYE034QgJCU.rcUWqpE9uMSH*3FcDv.nDQHhM!/b/dLwAAAAAAAAA&bo=pwKJAgAAAAADFxw!&rf=viewer_4) 

## **5.3** **数据结构与程序的关系**

老师/学生在进行操作时需对数据库访问数据结构，也就是对数据表进行查询和修改：修改个人信息时会对Teacher和Student表进行修改、老师设置课程会对Course表进行修改、每次讨论课都有对Seminar表、Presentation表、Score表等的操作修改。



# **6.** **运行设计**

## **6.1** **运行模块的组合**

| 名称         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| 讨论课       | 在课堂中以学生为主体的课堂方式，学生分小组展示，其他小组可以进行提问，老师给出指导意见 |
| 讨论课报告   | 在讨论课完成后报名讨论课的同学提交的书面总结报告             |
| 激活账户     | 教师或学生首次登录系统时需要激活账号。                       |
| 找回密码     | 可以找回密码。系统将自动发送原密码至所填写的联系方式上。     |
| 创建课程     | 教师可以创建需要开设讨论课的课程，并填写课程名称和设定分组规则 |
| 导入学生名单 | 教师可以在各个班级下导入该门课程选课学生名单（Excel文件格式）。 |
| 共享分组     | 教师可以向其他课程教师（可以是自己）发出共享分组请求。       |
| 设置轮次信息 | 教师可以设置轮次的相关信息，其中包括：在每个课程每个班级下设置每个Round；在此班级的报名次数（默认为1次）；在每个课程的每个Round中，教师设置各项成绩的计算方式（最高分、平均分，其中，最高分表示每个组在这个Round中所有报名的讨论课的最高成绩；平均分表示每个组在这个Round中所有报名的讨论课的平均成绩）。 |
| 发布讨论课   | 教师可以在课程下创建讨论课并发布                             |
| 共享讨论课   | 教师可以向同一课程的其他教师发出共享讨论课请求。             |
| 开始讨论课   | 教师可以开始某一课程某一班级已创建的讨论课。此时计时器从零开始计时，用于提醒教师上课进程。 |
| 抽取提问     | 教师可以在某一小组展示完毕后抽取提问，由系统对提问队列（用户不可见）进行抽取（抽取队首）。其中，提问队列（用户不可见）由系统根据公平性原则（在公平的基础上先到先得，保证所有组都有平等提问的机会）排出。 |
| 发言打分     | 教师可以对任意一组参与本次讨论课发言展示的小组打分、查看评分、修改评分。其中，每一次打分与修改评分时进行确认。 |
| 提问打分     | 教师可以对任意一组参与本次讨论课提问的小组打分、查看评分、修改评分。其中，每一次打分与修改评分时进行确认。 |
| 次序号       | 序号为自然数，连续且不能重复，当序列中某项的序号修改成功后序列中其他项的序号会相应的修改。 |

 



## **6.2** **运行控制**

**1. 教师：**

（1）登录：输入教工号和密码后点击“教师登录”登录系统。

（2）激活账户：首次登录系统时输入自定的新密码后点击“提交”激活账户。

（3）修改密码：登录账号，点击“个人中心”进入个人信息页面，点击“修改密码”，输入新密码，并再次输入新密码，点击“提交”修改密码成功。

（4）找回密码：输入系统自动发送的验证码，输入新密码，并再次输入新密码，点击“提交”，通过验证后找回密码成功。

（5）修改个人信息：在个人信息页面选择要修改的项进行修改，点击“保存”修改成功。

（6）登出：点击“退出登录”登出系统。

（7）创建课程：点击首页“创建新课程”进入创建课程页面，输入课程相关信息后点击“创建”创建成功。

（8）查看分组：在课程信息页点击“查看分组”查看当前分组情况。

（9）处理请求：含共享分组请求、共享讨论课请求、特殊分组审核请求，点击“同意”同意请求，点击“拒绝”拒绝请求。

（10）创建班级：在课程信息页点击“查看班级”进入班级列表，点击“创建新班级”进入创建班级页面，输入班级相关信息后点击“创建”创建成功。

（11）上传/查看学生名单：在班级信息页点击“上传学生名单”上传学生名单，点击“查看学生名单”查看学生名单。

（12）创建讨论课：在课程信息页点击“讨论课堂”进入讨论课页面，点击右上角“+”按钮进入创建讨论课页面，输入讨论课相关信息后点击“确认创建”创建成功，点击“修改信息”修改讨论课信息。

（13）批改报告：在讨论课页面点击“批改报告”进入批改报告页面，点击“下载”下载小组报告，选择相应分数后点击“打分”给该小组打分，点击“保存”保存分数。

（14）讨论课：在课程信息页点击“当前课堂”进入讨论课页面，选择班级、讨论课次序，点击“开始”开始讨论课，点击“开始/暂停时间”开始/暂停计时，点击“开始提问”开启提问环节，点击“结束提问”结束提问环节，点击“下一组展示”换下一个小组进行展示。

（15）查看成绩：在课程信息页面点击“查看成绩”进入成绩页面。

\2. 学生：

（1）登录：输入学号和密码后点击“学生登录”登录系统。

（2）激活账户：首次登录系统时输入自定的新密码后点击“提交”激活账户。

（3）修改密码：登录账号，点击“个人中心”进入个人信息页，点击“修改密码”，输入新密码并确认新密码，点击“提交”修改成功。

（4）找回密码：输入系统自动发送的验证码，输入新密码，并再次输入新密码，点击“提交”，通过验证后找回密码成功。

（5）修改个人信息：在个人信息页面选择要修改的项进行修改，点击“保存”修改成功。

（6）登出：点击“退出登录”登出系统。

（7）查看分组：在课程信息页点击“分组情况”查看当前分组情况。

（8）创建分组：在组队信息页内，若尚未组队，点击“创建小组”创建小组，点击“查看其他小组”查看其他小组分组情况，点击“查看未组队学生”查看尚未组队的学生名单。输入小组相关信息后点击“确认创建”完成创建。点击“申请特殊小组”发起申请特殊申请，点击“退出小组”退出小组。

（9）查看成绩：在课程信息页点击“查看成绩”查看成绩。

（10）报名/取消讨论课：在课程信息页面点击“讨论课堂”进入讨论课页面，选择对应讨论课，若未报名，选择展示顺序后点击“报名”报名讨论课。若报名成功，点击“取消报名”取消本次报名。

（11）上传文件：在讨论课页面点击“上传PPT”上传本次展示PPT材料，点击“上传报告”上传本次讨论课报告。

（12）讨论课：在课程信息页点击“当前课堂”进入讨论课，在提问环节点击“提问”发起提问，点击“下载该组PPT”下载该展示小组的展示文件。

## **6.3** **运行时间**

本系统在实现登录、创建课程、报名讨论课、评分、查看成绩等一系列操作时，内部处理过程较为简单，所需时间极短。因此影响运行时间的最主要因素是网络硬件。在网络硬件性能较好的情况下可以做到反应敏捷。

# **7.** **出错处理设计**

## **7.1** **出错输出信息**

| **出错信息**   | **出错原因**             | **输出信息**                 |
| -------------- | ------------------------ | ---------------------------- |
| 账号或密码错误 | 用户输入的账号或密码有误 | 账号或密码错误               |
| 报名失败       | 当前时间不在报名时间段内 | 当前不是报名时间段           |
| 无法上传报告   | 一般为格式错误           | 提示用户上传指定格式的文件   |
| 操作错误       | 用户的不正确操作         | 中止操作并提示用户正确的操作 |
| 查询分数出错   | 数据库数据出错           | 提示用户联系管理员查询       |

 

## **7.2** **出错处理对策**

\1. 由于系统算法本身并不复杂，若长时间没有取得服务器的响应，则可能为网络问题，此时应提醒用户检查网络。

\2. 若为用户自身操作不当导致的错误，则提示用户正确的操作。

\3. 若出现了数据传输的错误，则应检查数据库的算法正确与否，以及传输文件的相关算法是否正确，确保用户上传的数据以及查看的分数准确无误。

\4. 其他不可预知的错误：程序也会有一些我们无法预知或没考虑完全的错误，我们对此不可能做出安全的异常处理，这时我们主要要保证数据的安全，所以要经常的进行数据库备份。同时提醒用户对相关问题进行反馈，确保我们能够定位到漏洞的存在并找出解决办法。

# **8.** **安全保密设计**

在安全方面，老师和学生均需要登录才可使用系统。只有老师有修改课程信息、讨论课信息及评分等权限，故老师需要保管密码防止他人盗号。

# **9.** **维护设计**

功能方面的维护，由于本系统采用的是模块化的设计方法，每个模块之间相互独立性较高，这样对软件的维护带来了很大的便捷；对于单独功能的修改只需修改对应窗口；对于功能的添加，只要再添加菜单项的内容以及该功能即可。

 

 

 