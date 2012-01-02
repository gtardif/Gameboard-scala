package gameboard
import scala.actors.Actor
import gameboard.lift.comet.LiftCometGameServer

class P4Game(val name: String) extends Actor {
  var players = List[Player]()
  var board: P4Board = null
  var moves: List[(Int, Side.Side)] = List()

  def join[T <: Player](newPlayer: (Side.Side) => T): T = players match {
    case List() => {
      val player1 = newPlayer(Side.RED)
      players = List(player1)
      player1.start()
      player1
    }
    case player1 :: Nil => {
      val player2 = newPlayer(Side.YELLOW)
      players :+= player2
      player1.newMessage("player YELLOW joined the game")
      player2.start()
      start()
      player2
    }
    case _ => throw new IllegalArgumentException("This game is already started")
  }

  def started = players.length == 2
  def ended = board.gameEnded

  override def start() = {
    super.start()
    board = P4Board.newGame((players(0)).side)
    players.foreach(_ ! (board, this))
    players.foreach(_ ! "Game started ; RED starts playing")
    this
  }

  def play(column: Int, player: Player) {
    this ! (column, player)
  }

  def act() {
    receive {
      case (column: Int, player: Player) => {
        try {
          board = board.play(player.side, column)
          moves :+= (column, player.side)
          players.foreach(_ ! (board, this))
          if (board.gameEnded) {
            if (board.winner.isEmpty) players.foreach(_ ! "End of Game ; no winner")
            else players.foreach(_ ! ("winner is " + board.winner.get))
            LiftCometGameServer.gameEnded(this)
          }

        } catch {
          case e: IllegalArgumentException => player ! e.getMessage()
        }
        act()
      }
    }
  }

}
