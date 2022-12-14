package com.example.chat_bot.networking.Retrofit.Seeds_api.api

import McqsListss
import Quest
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_bot.Lists.*
import com.example.chat_bot.Room.Entities.Data
import com.example.chat_bot.Room.Entities.OnlineUserData
import com.example.chat_bot.data.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SEEDSViewModel constructor(private val repository: SEEDSRepository)  : ViewModel() {

    val mcqList = MutableLiveData<McqsListss>()
   // val matchList = MutableLiveData<Matchpairs>()
    val topicListss = MutableLiveData<TopicsList>()
    val gradeList = MutableLiveData<gradesList>()
    val agegrouplist = MutableLiveData<AgegroupList>()
    val subjectList = MutableLiveData<SubjectList>()
    val tfList = MutableLiveData<trufalses>()
    val userList = MutableLiveData<OnlineUserData>()
    val errorMessage = MutableLiveData<String>()
    val quizList = MutableLiveData<Quest>()
    val myresponse: MutableLiveData<Response<LoginData>> = MutableLiveData()
    val op_response: MutableLiveData<Response<openEndedResponse>> = MutableLiveData()


     fun getAllMcqs() {

         viewModelScope.launch {
             val response = repository.getAllMcqs()
             response.enqueue(object : Callback<McqsListss> {
                 override fun onResponse(call: Call<McqsListss>, response: Response<McqsListss>) {
                     if (response?.body() != null)
                     {mcqList.postValue(response.body())}

                 }
                 override fun onFailure(call: Call<McqsListss>, t: Throwable) {
                     errorMessage.postValue(t.message.toString())

                 }
             })
         }

    }
     fun getQuiz(topidID: String) {

        viewModelScope.launch {
            val res: Call<Quest> = repository.getQuiz(topidID)

            res.enqueue(object : Callback<Quest> {
                override fun onResponse(call: Call<Quest>, response: Response<Quest>) {
                    if (response?.body() != null)
                    {quizList.postValue(response.body())}

                }
                override fun onFailure(call: Call<Quest>, t: Throwable) {
                    errorMessage.postValue(t.message.toString())
                    Log.d("jaka", t.message.toString())

                }
            })
        }

    }

    fun getUserDatabyName(username: String) {

        viewModelScope.launch {
            val res: Call<OnlineUserData> = repository.getuserByName(username)

            res.enqueue(object : Callback<OnlineUserData> {
                override fun onResponse(call: Call<OnlineUserData>, response: Response<OnlineUserData>) {
                    if (response.body() != null)
                    {userList.postValue(response.body())}
                    else
                    {
                        errorMessage.postValue(response.code().toString())


                    }


                }

                override fun onFailure(call: Call<OnlineUserData>, t: Throwable) {
                    errorMessage.postValue(t.message.toString())
                    Log.d("jaka", t.message.toString())

                }
            })
        }

    }



//    fun getAllMatchingPairs() {
//
//        viewModelScope.launch {
//            val response = repository.getAllmatchpairs()
//            response.enqueue(object : Callback<Matchpairs> {
//                override fun onResponse(call: Call<Matchpairs>, response: Response<Matchpairs>) {
//                    if (response?.body() != null)
//                    {matchList.postValue(response.body())}
//
//                }
//                override fun onFailure(call: Call<Matchpairs>, t: Throwable) {
//                    errorMessage.postValue(t.message.toString())
//
//                }
//            })
//        }
//
//    }

     fun getAllGrades() {

         GlobalScope.launch (Dispatchers.IO){
        val response = repository.getAllGrades()
        response.enqueue(object : Callback<gradesList> {
            override fun onResponse(call: Call<gradesList>, response: Response<gradesList>) {
                if (response?.body() != null)
                {gradeList.postValue(response.body())}
            }
            override fun onFailure(call: Call<gradesList>, t: Throwable) {
                errorMessage.postValue(t.message.toString())
            }
        })
         }
    }

     fun getAllAgeGroups() {
         GlobalScope.launch (Dispatchers.IO) {
        val response = repository.getAllAgegroups()
        response.enqueue(object : Callback<AgegroupList> {
            override fun onResponse(call: Call<AgegroupList>, response: Response<AgegroupList>) {
                if (response?.body() != null)
                {agegrouplist.postValue(response.body())}

            }
            override fun onFailure(call: Call<AgegroupList>, t: Throwable) {
                errorMessage.postValue(t.message.toString())
            }
        })
         }
    }

     fun getAllTopics() {
         GlobalScope.launch(Dispatchers.IO) {
             val response = repository.getAllTopics()
             if(response.isSuccessful)
             {
                 run { topicListss.postValue(response.body()) }
             }
             else
             {
                 errorMessage.postValue(response.toString())
             }
//             response.enqueue(object : Callback<TopicsList> {
//                 override fun onResponse(call: Call<TopicsList>, response: Response<TopicsList>) {
//                     if (response?.body() != null)
//                     {topicListss.postValue(response.body())}
//                 }
//                 override fun onFailure(call: Call<TopicsList>, t: Throwable) {
//                     errorMessage.postValue(t.cause.toString())
//                 }
//             })
         }

    }

     fun getAllSubjects() {
         viewModelScope.launch {
             val response = repository.getAllSubjects()
             response.enqueue(object : Callback<SubjectList> {
                 override fun onResponse(call: Call<SubjectList>, response: Response<SubjectList>) {
                     if (response?.body() != null)
                     {subjectList.postValue(response.body())}
                 }
                 override fun onFailure(call: Call<SubjectList>, t: Throwable) {
                     errorMessage.postValue(t.cause.toString())
                 }
             })
         }

    }

     fun getAllTF() {
        val response = repository.getAllTF()
        response.enqueue(object : Callback<trufalses> {
            override fun onResponse(call: Call<trufalses>, response: Response<trufalses>) {
                if (response?.body() != null)
                {
                    response.body().toString()


                    tfList.postValue(response.body())



                }
            }
            override fun onFailure(call: Call<trufalses>, t: Throwable) {
                errorMessage.postValue(t.cause.toString())
            }
        })
    }
    fun create_user(user: Userz)
    {
        viewModelScope.launch {
            val response : Response<LoginData> = repository.createUser(user)
            myresponse.value = response
        }
    }

    fun submitOpenEnded(openEnded: OpenEnded)
    {
        viewModelScope.launch {
            val response : Response<openEndedResponse> = repository.submitOpenEnded(
                openEnded)
            op_response.value = response
        }
    }



//    fun create_user(user: User)
//    {
//        viewModelScope.launch {
//            val response : Call<LoginData> = repository.createUser(user)
//            response.enqueue(object : Callback<LoginData> {
//                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                    if (response?.body() != null)
//                    {
//                        response.body().toString()
//
//
//                        userList.postValue(response.body())
//
//
//
//                    }
//                }
//                override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                    errorMessage.postValue(t.cause.toString())
//                }
//            })
//
//        }
//    }

    fun login_user(user: Userz)
    {
        viewModelScope.launch {
            val response : Response<LoginData> = repository.loginUser(user)
            myresponse.value = response
        }
    }
//    fun login_user(user: User)
//    {
//        viewModelScope.launch {
//            val response : Call<LoginData> = repository.loginUser(user)
//            response.enqueue(object : Callback<LoginData> {
//                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                    if (response?.body() != null)
//                    {
//                        response.body().toString()
//
//                        userList.postValue(response.body())
//
//
//
//                    }
//                }
//                override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                    errorMessage.postValue(t.cause.toString())
//                }
//            })
//        }
//    }

//    fun login_user(user: User)
//    {
//        viewModelScope.launch {
//            val response : Call<LoginData> = repository.getUser(user)
//            response.enqueue(object : Callback<LoginData> {
//                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                    if (response?.body() != null)
//                    {
//                        response.body().toString()
//                        userList.postValue(response.body())
//
//                    }
//                }
//                override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                    errorMessage.postValue(t.cause.toString())
//                }
//            })
//        }
//    }
}

