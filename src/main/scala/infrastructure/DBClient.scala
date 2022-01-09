package us.wh.zlayerdemo
package infrastructure

import zio.*

case class DBClient()

object DBClient {
  val live: ULayer[DBClient] = ZLayer.succeed(DBClient())
}
