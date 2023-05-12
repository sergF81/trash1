package com.example.trash1.domain.usecase

import androidx.fragment.app.FragmentActivity
import com.example.trash1.data.Item
import com.example.trash1.data.Users
import com.example.trash1.domain.repository.InterfaceAPI
import com.example.trash1.presentation.ListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchUseCase(
    private val userDataLoader: UserDataLoader,
    //создаем массив, в котором будут храниться данные о логине пользователя
    var userArray: MutableList<String> = mutableListOf()

) {
    var pageNumber = 1

    // переменная для хранения ссылки к API серверу
    val baseUrl = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //функция для обработки результатов запроса Retrofit
    fun userRetrofit(searchWords: String, context: ListFragment) {

        val client: InterfaceAPI = retrofit.create(InterfaceAPI::class.java)
        val call: Call<Users<Item>> = client.getLoginUser("$searchWords in:login", pageNumber)
        call.enqueue(object
            : Callback<Users<Item>> {

            override fun onResponse(
                call: Call<Users<Item>>,
                response: Response<Users<Item>>
            ) {
                if (!response.isSuccessful) {
                    userDataLoader.errorData()
                    return

                } else {
                    val users = response.body()?.items.orEmpty()
                    users.forEach {
                        userArray.add(it.loginUser)
                    }
                    userDataLoader.dataLoader(userArray)
                    userArray.clear()
                }
            }

            override fun onFailure(call: Call<Users<Item>>, t: Throwable) {
                println(t)
            }
        })
    }

    interface UserDataLoader {
        fun dataLoader(userArray: MutableList<String>)

        //объявляем функцию для выведения Тоаста в случае превышения лимита подключений за 1 минуту.
        fun errorData()
    }


}