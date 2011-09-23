package gameboard.lift.comet

import net.liftweb.http.rest.RestHelper
import gameboard._
import net.liftweb.http.OkResponse
import net.liftweb.http.PlainTextResponse
import net.liftweb.json.JsonAST.JString
import net.liftweb.json.JsonAST.JField
import net.liftweb.json.JsonAST.JArray

object GameWebService extends RestHelper {
  serve {
    case "P4" :: "play" :: gameName :: side :: column :: _ JsonGet _ => {
      val game = LiftCometGameServer.game(gameName)
      val player: Player = game.players(if (side == Side.RED.toString()) 0 else 1)
      game.play(column.toInt, player)
      OkResponse()
    }
    
    case "P4" :: "newGame" :: gameName :: _ JsonGet _ => {
      LiftCometGameServer.newGame(gameName)
      OkResponse()
    }
  }
}