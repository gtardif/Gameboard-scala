package gameboard

object Side extends Enumeration{
  type Side = Value
  val RED, YELLOW = Value
  
  def other(side : Side) : Side.Side = if (side == RED) YELLOW else RED
}