package gameboard
import scala.actors.Actor

class P4Game(val players: List[Player]) extends Actor {
  require(players.size == 2, "There must be 2 players")
  var board = P4Board.newGame((players(0)).side)

  override def start() = {
    super.start()
    players.foreach(_.start())
    players.foreach(_ ! (board, this))
    this
  }
  
  def play(column : Int, player : Player) {
    this!(column, player)
  }


  def act() {
    receive {
      case (column: Int, player: Player) => {
        try {
          board = board.play(player.side, column)
          players.foreach(_ ! (board, this))
          if (board.gameEnded ) {
            if (board.winner.isEmpty) players.foreach(_ ! "End of Game ; no winner")
            else players.foreach(_ ! ("winner is " + board.winner.get))
          }

        } catch {
          case e: IllegalArgumentException => player!e.getMessage()
        }
        act()
      }
    }
  }

}
object P4Game extends Application {
  val player1 = new HumanPlayer(Side.RED)
  val player2 = new P4Bot(Side.YELLOW)
  val game = new P4Game(List(player1, player2))

  game.start()
}