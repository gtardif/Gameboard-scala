package gameboard

import Side._
import scala.collection.immutable.LinearSeq

class P4Board(val columns: List[List[Side]], val player: Side) {
  require(columns.size == 7, "P4 boad cannot have " + columns.size + "columns")

  val winner: Option[Side] = winner(columns)
  val gameEnded = winner.isDefined

  private def winner(columns: List[List[Side]]): Option[Side] = {
    val allColumns = columns.map(_.map(Some(_)))
    val allRaws = (0 to 5) map { rawIndex => columns map { column => if (column.size > rawIndex) Some(column(rawIndex)) else None } }

    val allDiag1 = (-6 to 7) map { diagIndex =>
      (columns zipWithIndex) map { columnWithIndex =>
        val (column, colIndex) = columnWithIndex
        val wantedHeight = diagIndex + colIndex
        if (column.size > wantedHeight && wantedHeight >= 0) Some(column(wantedHeight)) else None
      }
    }

    val allDiag2 = (-6 to 7) map { diagIndex =>
      (columns zipWithIndex) map { columnWithIndex =>
        val (column, colIndex) = columnWithIndex
        val wantedHeight = diagIndex - colIndex
        if (column.size > wantedHeight && wantedHeight >= 0) Some(column(wantedHeight)) else None
      }
    }

    winnerOnLines(allColumns) orElse winnerOnLines(allRaws) orElse winnerOnLines(allDiag1) orElse winnerOnLines(allDiag2) 
  }

  private def winnerOnLines(lines: Seq[Seq[Option[Side]]]): Option[Side] = {
    val align4Reds = List.fill(4)(Some(RED))
    val align4Yellows = List.fill(4)(Some(YELLOW))

    lines match {
      case lines if ((lines map { _.containsSlice(align4Reds) }).contains(true)) => Some(RED)
      case lines if ((lines map { _.containsSlice(align4Yellows) }).contains(true)) => Some(YELLOW)
      case _ => None
    }
  }

  def play(side: Side, columnIndex: Int): P4Board = {
    require(side == player, "It is not your turn to play," + side)
    require(columnIndex <= 6, "impossible to play column " + columnIndex)
    require(columns(columnIndex).size < 6, "impossible to add pawn in column " + columnIndex)

    new P4Board((columns zipWithIndex) map (colWithIndex => if (colWithIndex._2 == columnIndex) colWithIndex._1 :+ side else colWithIndex._1), other(player))
  }
}

object P4Board {
  val emptyColumns = List.fill(7)(List.empty)
  val empty = new P4Board(emptyColumns, Side.RED)

  def newGame(side: Side) = {
    new P4Board(List.fill(7)(List.empty), side)
  }
}