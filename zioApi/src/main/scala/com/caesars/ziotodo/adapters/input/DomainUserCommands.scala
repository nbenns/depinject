package com.caesars.ziotodo.adapters.input

import com.caesars.ziotodo.domain.model.User
import com.caesars.ziotodo.domain.ports.input.UserCommands
import com.caesars.ziotodo.domain.ports.output.UserRepository
import zio.{Has, ZIO, ZLayer}

import java.util.UUID

final class DomainUserCommands(userRepository: UserRepository) extends UserCommands {
  def addNewUser(firstName: String, lastName: String): ZIO[Any, Throwable, User] = {
    val id = UUID.randomUUID().toString
    val user = User(id, firstName, lastName, false)

    userRepository.save(user).as(user)
  }

  def banUser(userId: String): ZIO[Any, Throwable, User] =
    for {
      user    <- userRepository.findById(userId)
      updated  = user.copy(banned = true)
      _       <- userRepository.save(updated)
    } yield updated
}

object DomainUserCommands {
  val asLayer: ZLayer[Has[UserRepository], Nothing, Has[UserCommands]] =
    ZLayer.fromService(new DomainUserCommands(_))
}