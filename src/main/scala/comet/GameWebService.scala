package gameboard.comet

import net.liftweb.http.rest.RestHelper
import gameboard._
import net.liftweb.http.OkResponse

object GameWebService extends RestHelper {
  serve {
    case "P4" :: "play" :: column :: _ JsonGet _ => {
      println("server play " + column)
      val game: P4Game = GameServer.lastCreated.game
      val player: Player = game.players(0)
      game.play(column.toInt, player)
      OkResponse()
    }
  }
}