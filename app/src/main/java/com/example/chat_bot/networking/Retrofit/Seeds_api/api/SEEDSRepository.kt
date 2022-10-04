package com.example.chat_bot.networking.Retrofit.Seeds_api.api



import com.example.chat_bot.data.*
import retrofit2.Response


class SEEDSRepository constructor(private val retrofitService: SEEDSApi) {

     fun getAllMcqs() = retrofitService.getMcqs()

     fun getAllGrades() = retrofitService.getGrades()
     fun getAllAgegroups() = retrofitService.getAgegroups()
     suspend fun getAllTopics() = retrofitService.getallTopics()
     fun getAllSubjects() = retrofitService.getallSubjects()
     fun getAllTF() = retrofitService.getallTF()

    suspend  //fun getUser(user: User) = retrofitService.login_user(user )
    fun getQuiz(topicID: String) = retrofitService.getQuiz(topicID)

    fun getuserByName(name: String) = retrofitService.getUserbyUsername(name)

//    suspend fun getQuiz(topicID: String) : Response<Quizz> {
//        return retrofitService.getQuiz(topicID)
//    }


     suspend fun createUser(user: Userz) : Response<LoginData> {
        return retrofitService.create_user(user)
    }
    suspend fun loginUser(user: Userz) : Response<LoginData> {
        return retrofitService.login_user(user)
    }

    suspend fun submitOpenEnded(openEnded: OpenEnded): Response<openEndedResponse> {
        return retrofitService.submitOpenEnded(openEnded)
    }



}