package com.example.professionallearning.presenter;


import com.example.professionallearning.view.IAnalysisSpecialCallback;
import com.example.professionallearning.view.IChallengeCallback;
import com.example.professionallearning.view.IChallengeResultCallback;
import com.example.professionallearning.view.IDailyQuestionCallback;
import com.example.professionallearning.view.IDailyResultCallback;
import com.example.professionallearning.view.IQuestionCallBack;
import com.example.professionallearning.view.ISpecialCallback;
import com.example.professionallearning.view.ISpecialQuestionCallback;
import com.example.professionallearning.view.ISpecialResultCallback;
import com.example.professionallearning.view.IWeekCallback;
import com.example.professionallearning.view.IWeekQuestionCallback;
import com.example.professionallearning.view.IWeekResultCallback;

import java.util.List;

public interface IQuestionPresenter {
    //AnswerTypeActivity
    void getPerformanceInAnswerType(String userId);
    //根据周次更新该周题目已作答
    void updateIsAnswerByWeek(String week,String userId);
    //根据周次得到该周题目是否已作答
    void getIsAnswerByWeek(String week,String userId);
    //
    void getWeekScore(String date,String userId);
    //
    void insertWeekScore(String date,String userId,String score);
    //
    void updateWeekScore(String date,String userId,String score);
    //
    void getPerformanceInWeek(String userId);
    //
    void insertPerInWeek(String totalScore,String answerNum,String answerScore,String userId);
    //
    void updatePerformanceInWeek(String score,String userId);
    //
    void updateAnswerNumInWeek(String userId);
    //查找挑战答题数据
    void findAllChallengeQuestion();
    //插入挑战答题最高分
    void insertHighScore(String highScore,String userId);
    //查找挑战答题最高分
    void findHighScore(String userId);
    //更新挑战答题最高分
    void updateHighScore(String highScore,String userId);
    //挑战答题结果页中查找最高分
    void getScoreResult(String userId);
    //
    void getChallengeScore(String date,String userId);
    //
    void insertChallengeScore(String date,String userId,String score);
    //
    void updateChallengeScore(String date,String userId,String score);
    //
    void getPerformanceInChallenge(String userId);
    //
    void insertPerInChallenge(String totalScore,String answerNum,String answerScore,String userId);
    //
    void updatePerformanceInChallenge(String score,String userId);
    //
    void updateAnswerNumInChallenge(String userId);
    //用于WeekAnswerListActivity中获取周次
    void getWeek(String userId);
    //用于WeekAnswerListActivity
    void initUserWeek(String userId);
    //用于WeekAnswerActivity中获取题目
    void getQuestionByWeek();
    //用于DailyAnswerActivity获取每日答题数据
    void getDailyQuestion();
    //用于SpecialAnswerListActivity获取专项标题
    void getSpecial(String userId);
    //用于SpecialAnswerListActivity
    void initUserSpecial(String userId);
    //用于SpecialAnswerActivity获取专项答题题目
    void getSpecialQuestion();
    //用于SpecialAnswerActivity更新用户答案
    void updateUserAnswer(String userAnswer1, String userAnswer2, String userAnswer3, String userAnswer4, String userAnswer5,String pageNum, String specialTitle,String userId);
    //用于SpecialAnswerActivity更新用户答案
    void updateUserAnswer5(List<String> userAnswers, String specialTitle,String userId);
    //用于SpecialAnswerActivity更新是否中途退出
    void updateIsOut(String isOut,String specialTitle,String userId);
    //用于SpecialAnswerActivity继续答题时获取之前的答案
    void findUserAnswer(String specialTitle,String userId);
    //用于SpecialAnswerActivity
    void findUserAnswer5(String specialTitle,String userId);
    //用于SpecialAnswerActivity
    void insertUserAnswer(List<String> answers,String specialTitle,String pageNum,String userId);
    //用于SpecialAnswerActivity
    void insertUserAnswer5(List<String> questionAnswers,List<String> userAnswers,String specialTitle,String userId);
    //用于SpecialAnswerResultActivity获取用户答案
    void getUserAnswer(String specialTitle,String userId);
    //用于SpecialAnswerResultActivity更新题目作答状态
    void updateSpecialIsAnswer(String isAnswer,String isOut, String specialTitle,String userId);
    //用于SpecialAnswerResultActivity临时表中数据
    void updateUserTemp(String userAnswer1, String userAnswer2, String userAnswer3, String userAnswer4, String userAnswer5,String pageNum, String specialTitle,String userId);
    //
    void getIsAnswerBySpecial(String special,String userId);
    //
    void getSpecialScore(String date,String userId);
    //
    void insertSpecialScore(String date,String userId,String score);
    //
    void updateSpecialScore(String date,String userId,String score);
    //
    void getPerformanceInSpecial(String userId);
    //
    void insertPerInSpecial(String totalScore,String answerNum,String answerScore,String userId);
    //
    void updatePerformanceInSpecial(String score,String userId);
    //
    void updateAnswerNumInSpecial(String userId);
    //用于SpecialAnalysisActivity获取题目信息
    void findSpecialQuestion();
    //用于SpecialAnalysisActivity获取用户答案
    void getUserAnswer1(String specialTitle,String userId);
    //AnswerResultActivity
    void getDailyScore(String date,String userId);
    //AnswerResultActivity
    void insertDailyScore(String date,String userId,String score);
    //AnswerResultActivity
    void updateDailyScore(String date,String userId,String score);
    //
    void getPerformanceInDaily(String userId);
    //
    void insertPerInDaily(String totalScore,String answerNum,String answerScore,String userId);
    //AnswerResultActivity
    void updatePerformanceInDaily(String score,String userId);
    //AnswerResultActivity
    void updateAnswerNumInDaily(String userId);
    //注册获取题目、周次、专题的回调
    void registerQuestionCallback(IQuestionCallBack callBack);
    //取消注册获取题目、周次、专题的回调
    void unRegisterQuestionCallback(IQuestionCallBack callBack);
    //注册更新相应周次是否作答的回调
    void registerWeekCallback(IWeekResultCallback callBack);
    //取消注册更新相应周次是否作答的回调
    void unRegisterWeekCallback(IWeekResultCallback callBack);
    //注册更新挑战答题最高分的回调
    void registerChallengeCallback(IChallengeCallback callBack);
    //取消注册更新挑战答题最高分的回调
    void unRegisterChallengeCallback(IChallengeCallback callBack);
    //注册查询最高分结果的回调
    void registerChallengeResultCallback(IChallengeResultCallback callBack);
    //取消注册查询最高分结果的回调
    void unRegisterChallengeResultCallback(IChallengeResultCallback callBack);
    //注册用于WeekAnswerListActivity的回调
    void registerWeekListCallback(IWeekCallback callBack);
    //取消注册用于WeekAnswerListActivity的回调
    void unRegisterWeekListCallback(IWeekCallback callBack);
    //注册用于WeekAnswerActivity的回调
    void registerWeekQuestionCallback(IWeekQuestionCallback callBack);
    //取消注册用于WeekAnswerActivity的回调
    void unRegisterWeekQuestionCallback(IWeekQuestionCallback callBack);
    //注册用于DailyAnswerActivity的回调
    void registerDailyQuestionCallback(IDailyQuestionCallback callBack);
    //取消注册用于DailyAnswerActivity的回调
    void unRegisterDailyQuestionCallback(IDailyQuestionCallback callBack);
    //注册用于SpecialAnswerListActivity的回调
    void registerSpecialCallback(ISpecialCallback callBack);
    //取消注册用于SpecialAnswerListActivity的回调
    void unRegisterSpecialCallback(ISpecialCallback callBack);
    //注册用于SpecialAnswerActivity的回调
    void registerSpecialQuestionCallback(ISpecialQuestionCallback callBack);
    //取消注册用于SpecialAnswerActivity的回调
    void unRegisterSpecialQuestionCallback(ISpecialQuestionCallback callBack);
    //注册用于SpecialAnswerResultActivity的回调
    void registerSpecialResultCallback(ISpecialResultCallback callBack);
    //取消注册用于SpecialAnswerResultActivity的回调
    void unRegisterSpecialResultCallback(ISpecialResultCallback callBack);
    //注册用于SpecialAnalysisActivity的回调
    void registerSpecialAnalysisCallback(IAnalysisSpecialCallback callBack);
    //取消注册用于SpecialAnalysisActivity的回调
    void unRegisterSpecialAnalysisCallback(IAnalysisSpecialCallback callBack);
    //注册用于AnswerResultActivity的回调
    void registerDailyResultCallback(IDailyResultCallback callBack);
    //取消注册用于AnswerResultActivity的回调
    void unRegisterDailyResultCallback(IDailyResultCallback callBack);
}
