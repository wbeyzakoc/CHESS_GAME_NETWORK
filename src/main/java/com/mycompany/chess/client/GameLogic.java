package com.mycompany.chess.client;

public class GameLogic {

    public Piece[][] board = new Piece[8][8];

    public boolean move(Piece p, int newX, int newY) {
        if (!isLegalMove(p, newX, newY)) {
            return false;
        }

        MoveSnapshot snapshot = applyMove(p, newX, newY);
        snapshot.commitPromotion();
        return true;
    }

    public boolean isLegalMove(Piece p, int newX, int newY) {
        if (p == null || !inside(newX, newY)) {
            return false;
        }

        Piece target = board[newX][newY];
        if (target != null && target.color.equals(p.color)) {
            return false;
        }
        if (target != null && "sah".equals(target.type)) {
            return false;
        }

        if (!pieceCanReach(p, newX, newY, true)) {
            return false;
        }

        MoveSnapshot snapshot = applyMove(p, newX, newY);
        boolean safe = !isKingInCheck(p.color);
        snapshot.undo();
        return safe;
    }

    public boolean isKingInCheck(String color) {
        Piece king = findKing(color);
        if (king == null) {
            return true;
        }
        return isSquareAttacked(king.x, king.y, opposite(color));
    }

    public boolean hasAnyLegalMove(String color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board[x][y];
                if (p != null && p.color.equals(color)) {
                    for (int targetX = 0; targetX < 8; targetX++) {
                        for (int targetY = 0; targetY < 8; targetY++) {
                            if (isLegalMove(p, targetX, targetY)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean pieceCanReach(Piece p, int newX, int newY, boolean allowCastling) {
        int dx = newX - p.x;
        int dy = newY - p.y;
        Piece target = board[newX][newY];

        switch (p.type) {
            case "piyon":
                return pawnCanReach(p, dx, dy, target);
            case "at":
                return (Math.abs(dx) == 2 && Math.abs(dy) == 1)
                        || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
            case "kale":
                return (dx == 0 || dy == 0) && pathClear(p.x, p.y, newX, newY);
            case "fil":
                return Math.abs(dx) == Math.abs(dy) && pathClear(p.x, p.y, newX, newY);
            case "vezir":
                return (dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy))
                        && pathClear(p.x, p.y, newX, newY);
            case "sah":
                if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
                    return true;
                }
                return allowCastling && canCastle(p, newX, newY);
            default:
                return false;
        }
    }

    private boolean pawnCanReach(Piece p, int dx, int dy, Piece target) {
        int dir = p.color.equals("beyaz") ? -1 : 1;

        if (dx == 0 && dy == dir && target == null) {
            return true;
        }

        if (dx == 0 && dy == 2 * dir && !p.hasMoved
                && board[p.x][p.y + dir] == null && target == null) {
            return true;
        }

        return Math.abs(dx) == 1 && dy == dir && target != null;
    }

    private boolean canCastle(Piece king, int newX, int newY) {
        if (!"sah".equals(king.type) || king.hasMoved || newY != king.y || Math.abs(newX - king.x) != 2) {
            return false;
        }
        if (isKingInCheck(king.color)) {
            return false;
        }

        int rookX = newX > king.x ? 7 : 0;
        Piece rook = board[rookX][king.y];
        if (rook == null || !"kale".equals(rook.type) || !rook.color.equals(king.color) || rook.hasMoved) {
            return false;
        }

        int step = Integer.compare(newX, king.x);
        for (int x = king.x + step; x != rookX; x += step) {
            if (board[x][king.y] != null) {
                return false;
            }
        }

        String opponent = opposite(king.color);
        return !isSquareAttacked(king.x + step, king.y, opponent)
                && !isSquareAttacked(newX, newY, opponent);
    }

    private boolean isSquareAttacked(int x, int y, String attackerColor) {
        for (int px = 0; px < 8; px++) {
            for (int py = 0; py < 8; py++) {
                Piece attacker = board[px][py];
                if (attacker != null && attacker.color.equals(attackerColor)
                        && attacksSquare(attacker, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean attacksSquare(Piece p, int x, int y) {
        int dx = x - p.x;
        int dy = y - p.y;

        switch (p.type) {
            case "piyon":
                int dir = p.color.equals("beyaz") ? -1 : 1;
                return Math.abs(dx) == 1 && dy == dir;
            case "at":
                return (Math.abs(dx) == 2 && Math.abs(dy) == 1)
                        || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
            case "kale":
                return (dx == 0 || dy == 0) && pathClear(p.x, p.y, x, y);
            case "fil":
                return Math.abs(dx) == Math.abs(dy) && pathClear(p.x, p.y, x, y);
            case "vezir":
                return (dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy))
                        && pathClear(p.x, p.y, x, y);
            case "sah":
                return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
            default:
                return false;
        }
    }

    private boolean pathClear(int x1, int y1, int x2, int y2) {
        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);

        x1 += dx;
        y1 += dy;

        while (x1 != x2 || y1 != y2) {
            if (board[x1][y1] != null) {
                return false;
            }

            x1 += dx;
            y1 += dy;
        }
        return true;
    }

    private MoveSnapshot applyMove(Piece p, int x, int y) {
        MoveSnapshot snapshot = new MoveSnapshot(p, x, y);
        board[p.x][p.y] = null;
        p.x = x;
        p.y = y;
        p.hasMoved = true;
        board[x][y] = p;

        if (snapshot.castlingRook != null) {
            board[snapshot.castlingRookOldX][snapshot.castlingRookOldY] = null;
            snapshot.castlingRook.x = snapshot.castlingRookNewX;
            snapshot.castlingRook.y = snapshot.castlingRookOldY;
            snapshot.castlingRook.hasMoved = true;
            board[snapshot.castlingRook.x][snapshot.castlingRook.y] = snapshot.castlingRook;
        }

        return snapshot;
    }

    private Piece findKing(String color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board[x][y];
                if (p != null && "sah".equals(p.type) && p.color.equals(color)) {
                    return p;
                }
            }
        }
        return null;
    }

    public static String opposite(String color) {
        return "beyaz".equals(color) ? "siyah" : "beyaz";
    }

    private boolean inside(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private class MoveSnapshot {

        private final Piece moved;
        private final int oldX;
        private final int oldY;
        private final int newX;
        private final int newY;
        private final boolean oldHasMoved;
        private final String oldType;
        private final Piece captured;
        private final Piece castlingRook;
        private final int castlingRookOldX;
        private final int castlingRookOldY;
        private final int castlingRookNewX;
        private final boolean castlingRookOldHasMoved;

        MoveSnapshot(Piece moved, int newX, int newY) {
            this.moved = moved;
            this.oldX = moved.x;
            this.oldY = moved.y;
            this.newX = newX;
            this.newY = newY;
            this.oldHasMoved = moved.hasMoved;
            this.oldType = moved.type;
            this.captured = board[newX][newY];

            if ("sah".equals(moved.type) && Math.abs(newX - oldX) == 2) {
                this.castlingRookOldX = newX > oldX ? 7 : 0;
                this.castlingRookOldY = oldY;
                this.castlingRookNewX = newX > oldX ? newX - 1 : newX + 1;
                this.castlingRook = board[castlingRookOldX][castlingRookOldY];
                this.castlingRookOldHasMoved = castlingRook != null && castlingRook.hasMoved;
            } else {
                this.castlingRook = null;
                this.castlingRookOldX = -1;
                this.castlingRookOldY = -1;
                this.castlingRookNewX = -1;
                this.castlingRookOldHasMoved = false;
            }
        }

        void commitPromotion() {
            if ("piyon".equals(moved.type) && (newY == 0 || newY == 7)) {
                moved.type = "vezir";
            }
        }

        void undo() {
            board[newX][newY] = captured;
            moved.x = oldX;
            moved.y = oldY;
            moved.hasMoved = oldHasMoved;
            moved.type = oldType;
            board[oldX][oldY] = moved;

            if (castlingRook != null) {
                board[castlingRookNewX][castlingRookOldY] = null;
                castlingRook.x = castlingRookOldX;
                castlingRook.y = castlingRookOldY;
                castlingRook.hasMoved = castlingRookOldHasMoved;
                board[castlingRookOldX][castlingRookOldY] = castlingRook;
            }
        }
    }
}
