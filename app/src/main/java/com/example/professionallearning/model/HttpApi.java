package com.example.professionallearning.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpApi {

    @GET("/learn/getSpecialList")
    Call<ResponseBody> getSpecialList(@Query("userId") String userId);

    @POST("/learn/initUserSpecial")
    Call<ResponseBody> initUserSpecial(@Query("userId") String userId);

    @GET("/learn/getWeekList")
    Call<ResponseBody> getWeekList(@Query("userId") String userId);

    @POST("/learn/initUserWeek")
    Call<ResponseBody> initUserWeek(@Query("userId") String userId);

    @POST("/learn/updateIsAnswerByWeek")
    Call<ResponseBody> updateIsAnswerByWeek(@Query("week") String week,
                                            @Query("userId") String userId);

    @POST("/learn/insertHighScore")
    Call<ResponseBody> insertHighScore(@Query("highScore") String highScore,
                                       @Query("userId") String userId);

    @POST("/learn/updateHighScore")
    Call<ResponseBody> updateHighScore(@Query("highScore") String highScore,
                                       @Query("userId") String userId);

    @GET("/learn/findHighScore")
    Call<ResponseBody> findHighScore(@Query("userId") String userId);

    @GET("/learn/findAllChallengeQuestion")
    Call<ResponseBody> findAllChallengeQuestion();

    @GET("/learn/findAllWeekQuestion")
    Call<ResponseBody> findAllWeekQuestion();

    @GET("/learn/findIsAnswerByWeek")
    Call<ResponseBody> findIsAnswerByWeek(@Query("week") String week,
                                          @Query("userId") String userId);

    @GET("/learn/findAllDailyQuestion")
    Call<ResponseBody> findAllDailyQuestion();

    @POST("/learn/updateUserAnswer")
    Call<ResponseBody> updateUserAnswer(@Query("userAnswer1") String userAnswer1,
                                        @Query("userAnswer2") String userAnswer2,
                                        @Query("userAnswer3") String userAnswer3,
                                        @Query("userAnswer4") String userAnswer4,
                                        @Query("userAnswer5") String userAnswer5,
                                        @Query("pageNum") String pageNum,
                                        @Query("specialTitle") String specialTitle,
                                        @Query("userId") String userId);

    @POST("/learn/updateUserAnswer5")
    Call<ResponseBody> updateUserAnswer5(@Query("userAnswer1") String userAnswer1,
                                         @Query("userAnswer2") String userAnswer2,
                                         @Query("userAnswer3") String userAnswer3,
                                         @Query("userAnswer4") String userAnswer4,
                                         @Query("userAnswer5") String userAnswer5,
                                         @Query("specialTitle") String specialTitle,
                                         @Query("userId") String userId);

    @POST("/learn/updateSpecialIsOut")
    Call<ResponseBody> updateSpecialIsOut(@Query("isOut") String isOut,
                                          @Query("specialTitle") String specialTitle,
                                          @Query("userId") String userId);

    @GET("/learn/findAllSpecialQuestion")
    Call<ResponseBody> findAllSpecialQuestion();

    @GET("/learn/findUserAnswer")
    Call<ResponseBody> findUserAnswer(@Query("specialTitle") String specialTitle,
                                      @Query("userId") String userId);

    @POST("/learn/insertUserAnswer")
    Call<ResponseBody> insertUserAnswer(@Query("userAnswer1") String userAnswer1,
                                        @Query("userAnswer2") String userAnswer2,
                                        @Query("userAnswer3") String userAnswer3,
                                        @Query("userAnswer4") String userAnswer4,
                                        @Query("userAnswer5") String userAnswer5,
                                        @Query("specialTitle") String specialTitle,
                                        @Query("pageNum") String pageNum,
                                        @Query("userId") String userId);

    @POST("/learn/insertUserAnswer5")
    Call<ResponseBody> insertUserAnswer5(@Query("question1") String question1,
                                         @Query("question2") String question2,
                                         @Query("question3") String question3,
                                         @Query("question4") String question4,
                                         @Query("question5") String question5,
                                         @Query("userAnswer1") String userAnswer1,
                                         @Query("userAnswer2") String userAnswer2,
                                         @Query("userAnswer3") String userAnswer3,
                                         @Query("userAnswer4") String userAnswer4,
                                         @Query("userAnswer5") String userAnswer5,
                                         @Query("specialTitle") String specialTitle,
                                         @Query("userId") String userId);

    @GET("/learn/findUserAnswer5")
    Call<ResponseBody> findUserAnswer5(@Query("specialTitle") String specialTitle,
                                       @Query("userId") String userId);

    @POST("/learn/updateSpecialIsAnswer")
    Call<ResponseBody> updateSpecialIsAnswer(@Query("isAnswer") String isAnswer,
                                             @Query("isOut") String isOut,
                                             @Query("specialTitle") String specialTitle,
                                             @Query("userId") String userId);

    @GET("/learn/findEverydayScore")
    Call<ResponseBody> findEverydayScore(@Query("date") String date,
                                         @Query("userId") String userId);

    @POST("/learn/insertDailyScore")
    Call<ResponseBody> insertDailyScore(@Query("date") String date,
                                        @Query("userId") String userId,
                                        @Query("dailyScore") String dailyScore);

    @POST("/learn/updateDailyScore")
    Call<ResponseBody> updateDailyScore(@Query("date") String date,
                                        @Query("userId") String userId,
                                        @Query("dailyScore") String dailyScore);

    @POST("/learn/updateAnswerNum")
    Call<ResponseBody> updateAnswerNum(@Query("userId") String userId);

    @POST("/learn/updatePerformanceInDaily")
    Call<ResponseBody> updatePerformanceInDaily(@Query("score") String score,
                                                @Query("userId") String userId);

    @POST("/learn/insertWeekScore")
    Call<ResponseBody> insertWeekScore(@Query("date") String date,
                                       @Query("userId") String userId,
                                       @Query("weekScore") String weekScore);

    @POST("/learn/updateWeekScore")
    Call<ResponseBody> updateWeekScore(@Query("date") String date,
                                       @Query("userId") String userId,
                                       @Query("weekScore") String weekScore);

    @POST("/learn/insertSpecialScore")
    Call<ResponseBody> insertSpecialScore(@Query("date") String date,
                                          @Query("userId") String userId,
                                          @Query("specialScore") String specialScore);

    @GET("/learn/findIsAnswerBySpecial")
    Call<ResponseBody> findIsAnswerBySpecial(@Query("specialTitle") String specialTitle,
                                             @Query("userId") String userId);

    @POST("/learn/updateSpecialScore")
    Call<ResponseBody> updateSpecialScore(@Query("date") String date,
                                          @Query("userId") String userId,
                                          @Query("specialScore") String specialScore);

    @POST("/learn/insertChallengeScore")
    Call<ResponseBody> insertChallengeScore(@Query("date") String date,
                                            @Query("userId") String userId,
                                            @Query("challengeScore") String challengeScore);

    @POST("/learn/updateChallengeScore")
    Call<ResponseBody> updateChallengeScore(@Query("date") String date,
                                            @Query("userId") String userId,
                                            @Query("challengeScore") String challengeScore);

    @GET("/learn/findPerformance")
    Call<ResponseBody> findPerformance(@Query("userId") String userId);

    @GET("/learn/findUser")
    Call<ResponseBody> findUser(@Query("phone") String phone);

    @POST("/learn/insertUser")
    Call<ResponseBody> insertUser(@Query("phone") String phone,
                                  @Query("name") String name,
                                  @Query("password") String password);


    @POST("/learn/insertPerformance")
    Call<ResponseBody> insertPerformance(@Query("totalScore") String totalScore,
                                         @Query("answerNum") String answerNum,
                                         @Query("answerScore") String answerScore,
                                         @Query("userId") String userId);

    @GET("/learn/findAllPerformance")
    Call<ResponseBody> findAllPerformance();

    @GET("/learn/findArticle")
    Call<ResponseBody> findArticle();

    @GET("/learn/findCommentToArticle")
    Call<ResponseBody> findCommentToArticle(@Query("owner") int owner,
                                            @Query("type") int type);

    @POST("/learn/addCommentToArticle")
    Call<ResponseBody> addCommentToArticle(@Query("reviewer") String reviewer,
                                           @Query("review") String review,
                                           @Query("date") String date,
                                           @Query("articleId") int articleId,
                                           @Query("type") int type);

    @GET("/learn/findUserArticle")
    Call<ResponseBody> findUserArticle(@Query("articleId") int articleId,
                                       @Query("userId") String userId,
                                       @Query("articleType") int articleType);

    @POST("/learn/deleteUserArticle")
    Call<ResponseBody> deleteUserArticle(@Query("articleId") int articleId,
                                         @Query("userId") String userId,
                                         @Query("articleType") int articleType);

    @POST("/learn/insertUserArticle")
    Call<ResponseBody> insertUserArticle(@Query("articleId") int articleId,
                                         @Query("userId") String userId,
                                         @Query("articleType") int articleType,
                                         @Query("articleTitle") String articleTitle,
                                         @Query("date") String date);

    @GET("/learn/findUserCollect")
    Call<ResponseBody> findUserCollect(@Query("userId") String userId);

    @POST("/learn/deleteUserCollect")
    Call<ResponseBody> deleteUserCollect(@Query("id") int id);

    @GET("/learn/getVideoUri")
    Call<ResponseBody> getVideoUri();

    @GET("/learn/findArticleById")
    Call<ResponseBody> findArticleById(@Query("id") int id);

    @GET("/learn/findVideoById")
    Call<ResponseBody> findVideoById(@Query("id") int id);

    @POST("/learn/updateUserName")
    Call<ResponseBody> updateUserName(@Query("phone") String phone,
                                      @Query("name") String name);

    @POST("/learn/updateUserPhone")
    Call<ResponseBody> updateUserPhone(@Query("newPhone") String newPhone,
                                      @Query("oldPhone") String oldPhone);

    @POST("/learn/updateUserPas")
    Call<ResponseBody> updateUserPas(@Query("phone") String phone,
                                       @Query("pas") String pas);

}
