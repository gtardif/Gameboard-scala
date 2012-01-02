package gameboard

import scala.swing._
import swing.event._

class MainFrame (val player : HumanPlayer , var board : P4Board) {
    val boardPanel = new BoardPanel(board, player)

    val frame = new Frame {
      title = "Scala sample";

      contents = boardPanel;

      reactions += {
        case WindowClosing(_) => {
          exit();
        }
      }
    }

    frame.size = frame.contents(0).preferredSize
    frame.open()
}