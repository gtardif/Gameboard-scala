package gameboard

import Side._

class P4Bot(val side: Side.Side) extends Player {
  private val you = other(side)
  private val me = side

  def updateBoard(board: P4Board, game: P4Game) {
    if (board.player != side) {
      return
    }

    if (board.columns == P4Board.emptyColumns) {
      game.play(3, this)
      return
    }

    val columnScores = (board.columns).map {
      _.reverse match {
      case (this.me:: this.me:: this.me :: _) => 6
        case (this.you :: this.you :: this.you :: _) => 5
        case (column) if column.size == 6 => -1
        case (_) => 0
      }
    }

    val scoreWithColumnOrderedPerScore = (columnScores zipWithIndex).sortWith((score1, score2) => score1._1 > score2._1)
    game.play(scoreWithColumnOrderedPerScore.head._2, this)
  }

  def newMessage(message: String) {
    println("Bot received " + message)
  }
}