package gameboard

import Side._

class P4Bot(val side: Side.Side) extends Player {
  private val you = other(side)
  private val me = side
  private val four : ((=> Some[Side.Side]) => List[Some[Side.Side]]) = List.fill(4) _

  def updateBoard(board: P4Board, game: P4Game) {
    if (board.player != side) {
      return
    }

    if (board.columns == P4Board.emptyColumns) {
      game.play(3, this)
      return
    }

    val indexedColumnScores = (0 to board.columns.size - 1) map { (_, board.columns) } map { i =>
      val (index, columns) = i
      (score(index, board), index)
    }

    val scoreWithColumnOrderedPerScore = indexedColumnScores sortWith ((score1, score2) => score1._1 > score2._1)
    game.play(scoreWithColumnOrderedPerScore.head._2, this)
  }

  private def score(index: Int, board: P4Board): Int = {
    if (board.columns(index).size == 6) return -1

    val nextcolumns = board.play(side, index).columns
    val column = nextcolumns(index)

    column.reverse match {
      case (this.me :: this.me :: this.me :: this.me :: _) => 6
      case (this.me :: this.you :: this.you :: this.you :: _) => 5
      case (column) if column.size == 6 => -1
      case (_) => 0
    }

  }

  def newMessage(message: String) {
    println("Bot received " + message)
  }
}