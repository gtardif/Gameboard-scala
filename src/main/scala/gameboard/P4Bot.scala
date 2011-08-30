package gameboard

import Side._

class P4Bot(val side: Side.Side) extends Player {
  def updateBoard(board: P4Board, game: P4Game) {
    if (board.player != side) {
      return
    }

    val nbRed = (board.columns).map {
      _ match {
        case (YELLOW :: YELLOW :: YELLOW :: _) => 3
        case (_) => 0
      }
    }

    val colsWithNbReds = (nbRed zipWithIndex) groupBy (_._1)
    if (colsWithNbReds.contains(3)) {
      val colsWith3Red = colsWithNbReds(3)
      game.play(colsWith3Red(0)._2, this)
    }

    game.play(3, this)
  }

  def newMessage(message: String) {
    println("Bot received " + message)
  }
}