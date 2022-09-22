package br.com.alura.levels.dao

import br.com.alura.levels.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsersDao(
    users: List<User> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    init {
        Companion.users.addAll(users)
        CoroutineScope(dispatcher).launch {
            usersFlow.emit(users)
        }
    }

    companion object {
        private val users: MutableList<User> = mutableListOf()
        private val usersFlow = MutableStateFlow<List<User>>(emptyList())
    }

    fun autentica(
        nickname: String,
        password: String
    ) = users.firstOrNull {
        it.nickname == nickname &&
                it.password == password
    }

    fun findUserByNickname(nickname: String): User? =
        users.firstOrNull {
            it.nickname == nickname
        }

    fun save(user: User) {
        findUserByNickname(user.nickname)?.let {
            throw java.lang.IllegalArgumentException("This nickname already exists")
        }
        users.add(user)
    }

}