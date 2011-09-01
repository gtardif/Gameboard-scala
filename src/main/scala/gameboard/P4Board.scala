package gameboard

import Side._
import scala.actors.Actor

class P4Board(val columns: List[List[Side]], val player: Side)  {
  require(columns.size == 7, "P4 boad cannot have " + columns.size + "columns")

  def play(side: Side, columnIndex: Int) = {
    require(side == player, "It is not your turn to play," + side)
    require(columnIndex <= 6, "impossible to play column " + columnIndex)
    require(columns(columnIndex).size < 6, "impossible to add pawn in column " + columnIndex)

    new P4Board((columns zipWithIndex) map (colWithIndex => if (colWithIndex._2 == columnIndex) colWithIndex._1 :+ side else colWithIndex._1), other(player))
  }
}

object P4Board {
  val emptyColumns = List.fill(7)(List.empty)
  val empty = new P4Board(emptyColumns, Side.RED)
  
  def newGame(side : Side)  = {
    new P4Board(List.fill(7)(List.empty), side)
  }
}