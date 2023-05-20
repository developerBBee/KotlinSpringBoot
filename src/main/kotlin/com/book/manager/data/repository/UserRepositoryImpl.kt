package com.book.manager.data.repository

import com.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport
import com.book.manager.infrastructure.database.mapper.UserMapper
import com.book.manager.infrastructure.database.mapper.selectOne
import com.book.manager.infrastructure.database.record.UserRecord
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class UserRepositoryImpl(
    val mapper: UserMapper
) : UserRepository {
    override fun find(email: String): User? {
        val record = mapper.selectOne{
            where(UserDynamicSqlSupport.User.email, isEqualTo(email))
        }
        return record?.let { toModel(it) }
    }

    fun toModel(model: UserRecord): User {
        return User(
            id = model.id!!,
            email = model.email!!,
            password = model.password!!,
            name = model.name!!,
            roleType = model.roleType!!
        )
    }
}