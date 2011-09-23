package gameboard

import gameboard._

class GameStarter(val name: String) {
  private var players : List[Player] = List()
  private var startedGame: Option[P4Game] = None

  def game = startedGame getOrElse (throw new IllegalArgumentException("game not yet started ; missing " + (players.size - 2) + " players"))

  def started = startedGame isDefined

  def join[T <: Player](newPlayer : (Side.Side) => T): T = players match {
    case List() => {
      val player1 = newPlayer(Side.RED)
      players = List(player1)
      player1
    }
    case player1 :: Nil => {
      val player2 = newPlayer(Side.YELLOW)
      players = player2 :: players
      startedGame = Some(new P4Game(List(player1, player2)))
      player1.newMessage("player YELLOW joined the game")
      startedGame.get.start()
      player2
      
    }
    case _ => throw new IllegalArgumentException("This game is already started")
  }
}



