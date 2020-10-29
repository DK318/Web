package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        State curState = (State) session.getAttribute("state");
        if (curState == null) {
            curState = new State();
        }
        view.put("state", curState);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        State curState = (State) session.getAttribute("state");
        if (curState == null) {
            curState = new State();
        }
        int row = 0, col = 0;
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getKey().startsWith("cell")) {
                String curCell = entry.getKey();
                col = curCell.charAt(curCell.length() - 1) - '0';
                row = curCell.charAt(curCell.length() - 2) - '0';
                break;
            }
        }
        curState.cells[row][col] = (curState.crossesMove ? "X" : "O");
        if (checkWin(curState, row, col)) {
            curState.phase = (curState.crossesMove ? "WON_X" : "WON_O");
        }
        if (checkDraw(curState)) {
            curState.phase = "DRAW";
        }
        curState.crossesMove = !curState.crossesMove;
        view.put("state", curState);
        session.setAttribute("state", curState);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        State state = new State();
        session.setAttribute("state", state);
        view.put("state", state);
    }

    private boolean checkDraw(State state) {
        int cnt = 0;
        for (int i = 0; i < state.getSize(); i++) {
            for (int j = 0; j < state.getSize(); j++) {
                if (state.cells[i][j] != null) {
                    cnt++;
                }
            }
        }
        return cnt == state.getSize() * state.getSize();
    }

    private boolean checkWin(State state, int row, int col) {
        String mark = (state.crossesMove ? "X" : "O");
        int cntRow = 0, cntCol = 0, cntDiag = 0, cntRDiag = 0;
        for (int i = 0; i < state.getSize(); i++) {
            if (state.cells[row][i] != null && state.cells[row][i].equals(mark)) {
                cntCol++;
            }
            if (state.cells[i][col] != null && state.cells[i][col].equals(mark)) {
                cntRow++;
            }
            if (state.cells[i][i] != null && state.cells[i][i].equals(mark)) {
                cntDiag++;
            }
            if (state.cells[i][state.getSize() - 1 - i] != null && state.cells[i][state.getSize() - 1 - i].equals(mark)) {
                cntRDiag++;
            }
        }
        return cntCol == state.getSize() || cntRow == state.getSize() ||
                cntDiag == state.getSize() || cntRDiag == state.getSize();
    }

    public static class State {
        public String[][] cells = new String[4][4];
        public String phase = "RUNNING";
        public boolean crossesMove = true;

        public String[][] getCells() {
            return cells;
        }

        public String getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public int getSize() {
            return cells.length;
        }
    }
}
