package com.util;

public class MathUtil {

    // Array of the pixel widths of each ring, measured from the center of the castle
    // straight to the right between wedges 6 and 1.
    // Index 0 is outer edge of castle, index 1 outer edge swordsman, 2 outer knight, 3 outer archer, 4 outer forest.
    private static final int[] ringWidth = {100, 150, 200, 250, 300}; // TODO: get real numbers from Tim Scroggs

    // Array of the pixel heights of each ring, measured from the center of the castle
    // straight up in the center of wedge 2.
    private static final int[] ringHeight = {100, 150, 200, 250, 300}; // TODO: get real numbers from Tim Scroggs

    // Magnitude of slope of hypotenuse of 60 degree (PI/3 radian) right triangle.
    // This is the slope of the lines on the boundaries between rings within wedges 3 and 6. (upward slope)
    // (e.g. the line dividing the swordsman ring from the knight ring).
    // Negative this value is the slope of the lines on the boundaries between rings within wedges 1 and 4. (downward slope)
    private static final double cellSlope = Math.tan(Math.PI / 3);

    // 2d array of starting x coordinates of a monster in each of the 30 cells of the board:
    // first index is wedge number (1 through 6). 2nd index is distance:
    // 0 is castle, 1 is swordsman, 2 is knight, 3 is archer, 4 is forest
    private static final int[][] firstMonstXCoords = {
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0}}; // TODO: get real numbers from Tim Scroggs

    // 2d array of starting y coordinates of a monster in each of the 30 cells of the board:
    // first index is wedge number (1 through 6). 2nd index is distance:
    // 0 is castle, 1 is swordsman, 2 is knight, 3 is archer, 4 is forest
    private static final int[][] firstMonstYCoords = {
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0}}; // TODO: get real numbers from Tim Scroggs

    // How far apart adjacent monsters appear, in pixels. 
    // slantyDeltaX and slantyDeltaY apply to wedges 1, 3, 4, 6 (with diagonal lines separating cells).
    // straightDeltaX applies to wedges 2 and 5 (with horizontal lines separating cells).
    private static final int slantyDeltaX = 20;
    private static final int slantyDeltaY = 40;
    private static final int straightDeltaX = 50;

     /**
     * Given a wedge number and ring number and a monster number,
     * determine the y coordinate where to draw the monster.
     * Assuming origin top left corner, positive y axis down.
     * @param wedge Desired wedge number. Must be 1, 2, 3, 4, 5, or 6.
     * @param ring Desired ring number. Must 1 for castle, 2 for swordsman, 3
     * for knight, 4 for archer, or 5 for forest.
     * @param monsterNumber Sequence number of monster in this cell. For
     * example, regarding the third monster in the cell, provide argument 3 to
     * this parameter. Must be 1 or greater.
     * @param castleCenterX x coordinate of the center of the castle.
     * @param castleCenterY x coordinate of the center of the castle.
     * @param scaleCoeff Scaling coefficient of the hexagonal game board,
     * relative to the original artwork. For example, if the game board is being
     * displayed at 0.5 the size of the original full-size artwork, then provide
     * 0.5 as the argument to this parameter. Must be strictly positive.
     * @return The y coordinate at which to place the monster. Assuming origin
     * top left corner, positive y axis down. Returns -1 if the wedge isn't
     * between 1 and 6, or if the ring isn't between 1 and 5, or if the
     * monsterNumber is less than 0, or if the castle is not on screen e.g. has
     * a negative coordinate, or if scaleCoeff is nonpositive,
     * or if monsterNumber is less than 1.
     */
    public static int yCoord(int wedge, int ring, int monsterNumber, int castleCenterX, int castleCenterY, double scaleCoeff) {
        // step 0: error checking
        if (wedge < 1 || wedge > 6) {
            System.err.println("Error in MathUtil.xCoord: given wedge number " + wedge + " does not exist.");
            return -1;
        }
        if (ring < 1 || ring > 5) {
            System.err.println("Error in MathUtil.xCoord: given ring number " + ring + " does not exist.");
            return -1;
        }
        if (castleCenterX < 0 || castleCenterY < 0) {
            System.err.println("Error in MathUtil.xCoord: Given castle center point "
                    + "(" + castleCenterX + ", " + castleCenterY + ") is outside screen.");
            return -1;
        }
        if (scaleCoeff <= 0.0) {
            System.err.println("Error in MathUtil.xCoord: given scale coefficient " + scaleCoeff + " is nonpositive.");
            return -1;
        }
        if (monsterNumber < 1) {
            System.err.println("Error in MathUtil.xCoord: given monster number " + monsterNumber + " is less than 1.");
            return -1;
        }

        // step 0.5: declare local variables
        int startX, startY; // starting location of first monster in this wedge and ring
        int finalX, finalY; // final calculated location where monster will go
        int deltaX, deltaY; // distance between 
        int mult; // Multiplier of how many deltas to move this monster from starting location of this cell.
                        // 0 means keep in starting location.
                        // 1 means move one "space" to the "left". -1 means move one "space" to the right, etc.

        // step 1: Look up starting location for the given wedge and ring, and calculate delta x and y for this cell.
        startX = firstMonstXCoords[wedge - 1][ring - 1]; // -1 needed because 0 indexing of arrays
        startY = firstMonstYCoords[wedge - 1][ring - 1];
        
        if (wedge == 2) { // top horizontal wedge
            deltaX = -straightDeltaX; // 2nd monster goes leftward. negative sign on X
            deltaY = 0; // horizontal cell. no change in Y
        } else if(wedge == 5) { // bottom horizontal wedge 
            deltaX = straightDeltaX; // 2nd monster goes right. positive sign on Y
            deltaY = 0; // horizontal cell. no change in Y
        } else { // slanty wedge 1 3 4 or 6
            deltaX = slantyDeltaX;
            deltaY = slantyDeltaY;
            switch (wedge) { // flip sign of deltas based on which wedge we're in
                case 1:
                    deltaX *= -1; // wedge 1, 2nd monster goes leftward. negative sign on X
                    // wedge 2, 2nd monster goes upward. positive sign on Y
                    break;
                case 3:
                    deltaX *= -1; // wedge 3, 2nd monster goes leftward. negative sign on X
                    deltaY *= -1; // wedge 3, 2nd monster goes down. negative sign on Y
                    break;
                case 4:
                    // wedge 4, 2nd monster goes right. positive sign on X
                    deltaY *= -1; // wedge 4, 2nd monster goes downward. negative sign on Y
                    break;
                case 6:
                    // wedge 6, 2nd monster goes right and up. positive signs on X and Y
                    break;
                default:
                    System.out.println("Logic error in MathUtil.xCoord, wedge " + wedge + ".");
                    return -1;
            }
        }

        // step 2: calculate multiplier based on monsterNumber.
        // For monsterNumber 1,  2,  3,  4,  5,  6,  7,  8,  9, etc 
        // we get mult value 0,  1, -1,  2, -2,  3, -3,  4, -4, etc
        // Note: Integer division by 2 truncates, intentionally.
        mult = monsterNumber / 2; // now, multiplier has the right absolute value
        // Next, flip the sign on odd monsterNumbers, so every other monster goes "right" instead of "left"
        if(monsterNumber % 2 == 1) { // odd monster number, so go "right" instead of "left"
            mult *= -1;
        }
        
        // step 3: calculate coordinates of the monster using deltas and multiplier
        finalX = startX + mult * deltaX;
        finalY = startY + mult * deltaY;
        
        // step 4: translate coords of monster so origin is back to top left corner, positive y axis down.
        finalX = castleCenterX + finalX;
        finalY = castleCenterY - finalY;
        
        return finalY;
    } // end of yCoord method
    
    /**
     * Given a wedge number and ring number and a monster number,
     * determine the x coordinate where to draw the monster.
     * Assuming origin top left corner, positive y axis down.
     * @param wedge Desired wedge number. Must be 1, 2, 3, 4, 5, or 6.
     * @param ring Desired ring number. Must 1 for castle, 2 for swordsman, 3
     * for knight, 4 for archer, or 5 for forest.
     * @param monsterNumber Sequence number of monster in this cell. For
     * example, regarding the third monster in the cell, provide argument 3 to
     * this parameter. Must be 1 or greater.
     * @param castleCenterX x coordinate of the center of the castle.
     * @param castleCenterY x coordinate of the center of the castle.
     * @param scaleCoeff Scaling coefficient of the hexagonal game board,
     * relative to the original artwork. For example, if the game board is being
     * displayed at 0.5 the size of the original full-size artwork, then provide
     * 0.5 as the argument to this parameter. Must be strictly positive.
     * @return The x coordinate at which to place the monster. Assuming origin
     * top left corner, positive y axis down. Returns -1 if the wedge isn't
     * between 1 and 6, or if the ring isn't between 1 and 5, or if the
     * monsterNumber is less than 0, or if the castle is not on screen e.g. has
     * a negative coordinate, or if scaleCoeff is nonpositive,
     * or if monsterNumber is less than 1.
     */
    public static int xCoord(int wedge, int ring, int monsterNumber, int castleCenterX, int castleCenterY, double scaleCoeff) {
        // step 0: error checking
        if (wedge < 1 || wedge > 6) {
            System.err.println("Error in MathUtil.xCoord: given wedge number " + wedge + " does not exist.");
            return -1;
        }
        if (ring < 1 || ring > 5) {
            System.err.println("Error in MathUtil.xCoord: given ring number " + ring + " does not exist.");
            return -1;
        }
        if (castleCenterX < 0 || castleCenterY < 0) {
            System.err.println("Error in MathUtil.xCoord: Given castle center point "
                    + "(" + castleCenterX + ", " + castleCenterY + ") is outside screen.");
            return -1;
        }
        if (scaleCoeff <= 0.0) {
            System.err.println("Error in MathUtil.xCoord: given scale coefficient " + scaleCoeff + " is nonpositive.");
            return -1;
        }
        if (monsterNumber < 1) {
            System.err.println("Error in MathUtil.xCoord: given monster number " + monsterNumber + " is less than 1.");
            return -1;
        }

        // step 0.5: declare local variables
        int startX, startY; // starting location of first monster in this wedge and ring
        int finalX, finalY; // final calculated location where monster will go
        int deltaX, deltaY; // distance between 
        int mult; // Multiplier of how many deltas to move this monster from starting location of this cell.
                        // 0 means keep in starting location.
                        // 1 means move one "space" to the "left". -1 means move one "space" to the right, etc.

        // step 1: Look up starting location for the given wedge and ring, and calculate delta x and y for this cell.
        startX = firstMonstXCoords[wedge - 1][ring - 1]; // -1 needed because 0 indexing of arrays
        startY = firstMonstYCoords[wedge - 1][ring - 1];
        
        if (wedge == 2) { // top horizontal wedge
            deltaX = -straightDeltaX; // 2nd monster goes leftward. negative sign on X
            deltaY = 0; // horizontal cell. no change in Y
        } else if(wedge == 5) { // bottom horizontal wedge 
            deltaX = straightDeltaX; // 2nd monster goes right. positive sign on Y
            deltaY = 0; // horizontal cell. no change in Y
        } else { // slanty wedge 1 3 4 or 6
            deltaX = slantyDeltaX;
            deltaY = slantyDeltaY;
            switch (wedge) { // flip sign of deltas based on which wedge we're in
                case 1:
                    deltaX *= -1; // wedge 1, 2nd monster goes leftward. negative sign on X
                    // wedge 2, 2nd monster goes upward. positive sign on Y
                    break;
                case 3:
                    deltaX *= -1; // wedge 3, 2nd monster goes leftward. negative sign on X
                    deltaY *= -1; // wedge 3, 2nd monster goes down. negative sign on Y
                    break;
                case 4:
                    // wedge 4, 2nd monster goes right. positive sign on X
                    deltaY *= -1; // wedge 4, 2nd monster goes downward. negative sign on Y
                    break;
                case 6:
                    // wedge 6, 2nd monster goes right and up. positive signs on X and Y
                    break;
                default:
                    System.out.println("Logic error in MathUtil.xCoord, wedge " + wedge + ".");
                    return -1;
            }
        }

        // step 2: calculate multiplier based on monsterNumber.
        // For monsterNumber 1,  2,  3,  4,  5,  6,  7,  8,  9, etc 
        // we get mult value 0,  1, -1,  2, -2,  3, -3,  4, -4, etc
        // Note: Integer division by 2 truncates, intentionally.
        mult = monsterNumber / 2; // now, multiplier has the right absolute value
        // Next, flip the sign on odd monsterNumbers, so every other monster goes "right" instead of "left"
        if(monsterNumber % 2 == 1) { // odd monster number, so go "right" instead of "left"
            mult *= -1;
        }
        
        // step 3: calculate coordinates of the monster using deltas and multiplier
        finalX = startX + mult * deltaX;
        finalY = startY + mult * deltaY;
        
        // step 4: translate coords of monster so origin is back to top left corner, positive y axis down.
        finalX = castleCenterX + finalX;
        finalY = castleCenterY - finalY;
        
        return finalX;
    } // end of xCoord method

    /**
     * Return the ring number of the given coordinate pair. Always 1 through 6
     * unless error. 1 is castle ring. 2 is swordsman ring. 3 is knight ring. 4
     * is archer ring. 5 is forest ring. 6 indicates OUTSIDE the board beyond
     * the forest ring.
     *
     * @param x pixel x coordinate of the desired point.
     * @param y pixel y coordinate of the desired point.
     * @param castleCenterX x coordinate of the center of the castle.
     * @param castleCenterY y coordinate of the center of the castle.
     * @param scaleCoeff Scaling coefficient of the hexagonal game board,
     * relative to the original artwork. For example, if the game board is being
     * displayed at 0.5 the size of the original full-size artwork, then provide
     * 0.5 as the argument to this parameter. Must be strictly positive.
     * @return The ring number of the given coordinate pair, 1 through 6.
     * Returns -1 if coordinate pair is off screen, e.g. x coordinate or y
     * coordinate below 0, or if the center of the castle is off screen, e.g. a
     * coordinate of castle below 0, or if scaleCoeff is nonpositive. With
     * proper inputs, always returns between 1 and 6.
     */
    public static int ringNum(int x, int y, int castleCenterX, int castleCenterY, double scaleCoeff) {
        // step 0: error checking
        if (x < 0) {
            System.err.println("Error in MathUtil.ringNum: given x coordinate " + x + " is negative.");
            return -1;
        }
        if (y < 0) {
            System.err.println("Error in MathUtil.ringNum: given y coordinate " + y + " is negative.");
            return -1;
        }
        if (castleCenterX < 0 || castleCenterY < 0) {
            System.err.println("Error in MathUtil.ringNum: Given castle center point "
                    + "(" + castleCenterX + ", " + castleCenterY + ") is outside screen.");
            return -1;
        }
        if (scaleCoeff <= 0.0) {
            System.err.println("Error in MathUtil.ringNum: given scale coefficient " + scaleCoeff + " is nonpositive.");
            return -1;
        }

        // step 0.5: local variables
        int xPrime, yPrime; // stores (x, y) after translation of the origin.
        int wedge = MathUtil.wedgeNum(x, y, castleCenterX, castleCenterY); // which wedge we're in
        // Math fact: equation of a line is y = mx + b. Given point (xPrime, yPrime).
        // Math fact: If yPrime > m*xPrime + b, then given point is "above" the line. Replace the > with a < for "below" the line.
        double m; // used to store slope of a line we're going to compare with
        double[] bOuterEdges = new double[5]; // used to store y intercepts of lines on outside of each ring

        // step 1: translate coordinate pair so origin is in the middle of the screen,
        // and the positive y axis goes upward. positive x axis continues to go right.
        xPrime = x - castleCenterX;
        yPrime = castleCenterY - y;

        // step 2: calculate which ring we're in. The way of calculating this depends on which wedge we're in.
        switch (wedge) {
            case 1: // wedge 1, top right
                // Math fact: equation of a line is y = mx + b. Given point (xPrime, yPrime).
                // Math fact: If yPrime > m*xPrime + b, then given point is "above" the line
                m = -cellSlope; // negative slope
                for (int i = 0; i < bOuterEdges.length; i++) { // Adam did muchus muchus algebra!
                    bOuterEdges[i] = scaleCoeff * ringWidth[i] * cellSlope; // positive y intercepts
                }
                if (yPrime < m * xPrime + bOuterEdges[0]) { //given point is "under" line at outer edge of castle ring 
                    return 1; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[1]) {
                    return 2; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[2]) {
                    return 3; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[3]) {
                    return 4; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[4]) {
                    return 5; // in castle ring
                } else { // must be outside the game board
                    return 6; // outside the line on the outer edge of the forest ring
                }
            case 3: // wedge 3, top left
                m = cellSlope; // positive slope
                for (int i = 0; i < bOuterEdges.length; i++) {
                    bOuterEdges[i] = scaleCoeff * ringWidth[i] * cellSlope; // positive y intercepts
                }
                if (yPrime < m * xPrime + bOuterEdges[0]) { //given point is "under" line at outer edge of castle ring 
                    return 1; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[1]) {
                    return 2; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[2]) {
                    return 3; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[3]) {
                    return 4; // in castle ring
                } else if (yPrime < m * xPrime + bOuterEdges[4]) {
                    return 5; // in castle ring
                } else { // must be outside the game board
                    return 6; // outside the line on the outer edge of the forest ring
                }
            case 4: // wedge 4, bottom left
                m = -cellSlope; // negative slope
                for (int i = 0; i < bOuterEdges.length; i++) {
                    bOuterEdges[i] = -scaleCoeff * ringWidth[i] * cellSlope; // negative y intercepts
                }
                if (yPrime > m * xPrime + bOuterEdges[0]) { //given point is "above" line at outer edge of castle ring 
                    return 1; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[1]) {
                    return 2; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[2]) {
                    return 3; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[3]) {
                    return 4; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[4]) {
                    return 5; // in castle ring
                } else { // must be outside the game board
                    return 6; // outside the line on the outer edge of the forest ring
                }
            case 6: // wedge 6, bottom right
                m = cellSlope; // positive slope
                for (int i = 0; i < bOuterEdges.length; i++) {
                    bOuterEdges[i] = -scaleCoeff * ringWidth[i] * cellSlope; // negative y intercepts
                }
                if (yPrime > m * xPrime + bOuterEdges[0]) { //given point is "above" line at outer edge of castle ring 
                    return 1; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[1]) {
                    return 2; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[2]) {
                    return 3; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[3]) {
                    return 4; // in castle ring
                } else if (yPrime > m * xPrime + bOuterEdges[4]) {
                    return 5; // in castle ring
                } else { // must be outside the game board
                    return 6; // outside the line on the outer edge of the forest ring
                }
            case 5: // wedge 5, bottom middle
                yPrime *= -1; // turn yPrime from negative to positive, then treat it like wedge 2.
            // Note: No break statement here. using fall-through.
            case 2: // wedge 2, top middle
                if (yPrime <= scaleCoeff * ringHeight[0]) {
                    return 1; // in castle ring
                } else if (yPrime <= scaleCoeff * ringHeight[1]) {
                    return 2; // in swordsman ring
                } else if (yPrime <= scaleCoeff * ringHeight[2]) {
                    return 3; // in knight ring
                } else if (yPrime <= scaleCoeff * ringHeight[3]) {
                    return 4; // in archer ring
                } else if (yPrime <= scaleCoeff * ringHeight[4]) {
                    return 5; // in forest ring
                } else { // we must be beyond the forest ring
                    return 6; // outside the board
                }
            default:
                System.out.println("Error in MathUtil.ringNum: Invalid wedge number "
                        + wedge + " for point (" + x + ", " + y + ").");
                return -1;
        }
    } // end of method ringNum

    /**
     * Computes and returns the wedge number of a given coordinate pair.
     * Assuming the origin is in the top left corner, positive x axis goes to
     * the right, and positive y axis goes downward.
     *
     * @param x pixel x coordinate of the desired point.
     * @param y pixel y coordinate of the desired point.
     * @param castleCenterX x coordinate of the center of the castle.
     * @param castleCenterY y coordinate of the center of the castle.
     * @return The wedge number of the given coordinate pair. Returns -1 if
     * coordinate pair is off screen, e.g. x coordinate or y coordinate below 0,
     * or if the center of the castle is off screen, e.g. a coordinate of castle
     * below 0. With proper inputs, always returns between 1 and 6. Starting at
     * the positive x axis (on the right) and going clockwise, the wedge numbers
     * in order are: 1, 2, 3, 4, 5, 6.
     */
    public static int wedgeNum(int x, int y, int castleCenterX, int castleCenterY) {
        // step 0: error checking
        if (x < 0) {
            System.err.println("Error in MathUtil.wedgeNum: given x coordinate " + x + " is negative.");
            return -1;
        }
        if (y < 0) {
            System.err.println("Error in MathUtil.wedgeNum: given y coordinate " + y + " is negative.");
            return -1;
        }
        if (castleCenterX < 0 || castleCenterY < 0) {
            System.err.println("Error in MathUtil.wedgeNum: Given castle center point "
                    + "(" + castleCenterX + ", " + castleCenterY + ") is outside screen.");
            return -1;
        }

        // step 0.5: local variables
        int xPrime, yPrime; // stores (x, y) after translation of the origin.
        double theta; // stores angle
        double r; // distance (i.e. "radius") from origin to the point (xPrime, yPrime)

        // step 1: translate coordinate pair so origin is in the middle of the screen,
        // and the positive y axis goes upward. positive x axis continues to go right.
        xPrime = -castleCenterX + x;
        yPrime = castleCenterY - y;

        // step 2: Compute angle of the line from the origin to the given point.
        r = Math.sqrt((double) ((xPrime * xPrime) + (yPrime * yPrime))); // pythagorean theorem: euclid dist from origin to (xPrime, yPrime)
        theta = Math.acos(xPrime / r); // acos always return a number between 0 and PI -- this is annoying!!!
        if (y > castleCenterY) { // if input y coord is "below" the middle of the castle
            theta = Math.PI * 2 - theta; // then adjust theta accordingly
        }

        // System.out.println("Angle is " + theta/Math.PI + "*PI radians."); // test print statement
        // step 3: Use angle to determine wedge number.
        if (0.0 <= theta && theta <= Math.PI / 3) { // wedge 1, right above positive x axis, top right
            return 1;
        } else if (Math.PI / 3 <= theta && theta <= Math.PI * 2 / 3) { // wedge 2, at top middle of the board
            return 2;
        } else if (Math.PI * 2 / 3 <= theta && theta <= Math.PI) { // wedge 3, just above the negative x axis, top left
            return 3;
        } else if (Math.PI <= theta && theta <= Math.PI * 4 / 3) { // wedge 4, just above the negative x axis, bottom left
            return 4;
        } else if (Math.PI * 4 / 3 <= theta && theta <= Math.PI * 5 / 3) { // wedge 5, at bottom middle of the board
            return 5;
        } else { // it must be wedge 6, just below the positive x axis, bottom right
            return 6;
        }
    } // end of method wedgeNum

    /**
     * Return the angle of the given point with the center of the castle,
     * between 0 and two PI. Angles are measured in radians, clockwise from the
     * positive x axis on the right.
     *
     * @param x pixel x coordinate of the desired point.
     * @param y pixel y coordinate of the desired point.
     * @param castleCenterX x coordinate of the center of the castle.
     * @param castleCenterY y coordinate of the center of the castle.
     *
     * @return The angle of the point (x,y) with the center of the castle
     * (castleCenterX, castleCenterY), which is always between 0 and 2*Math.PI.
     * Angles are measured in radians, clockwise from the positive x axis on the
     * right. Returns -1 if coordinate pair is off screen, e.g. x coordinate or
     * y coordinate below 0, or if the center of the castle is off screen, e.g.
     * a coordinate of castle below 0.
     */
    public static double angleWithPoint(int x, int y, int castleCenterX, int castleCenterY) {
        // step 0: error checking
        if (x < 0) {
            System.err.println("Error in MathUtil.angleWithPoint: given x coordinate " + x + " is negative.");
            return -1;
        }
        if (y < 0) {
            System.err.println("Error in MathUtil.angleWithPoint: given y coordinate " + y + " is negative.");
            return -1;
        }
        if (castleCenterX < 0 || castleCenterY < 0) {
            System.err.println("Error in MathUtil.angleWithPoint: Given castle center point "
                    + "(" + castleCenterX + ", " + castleCenterY + ") is outside screen.");
            return -1;
        }

        // step 0.5: local variables
        int xPrime, yPrime; // stores (x, y) after translation of the origin.
        double theta; // stores angle
        double r; // distance (i.e. "radius") from origin to the point (xPrime, yPrime)

        // step 1: translate coordinate pair so origin is in the middle of the screen,
        // and the positive y axis goes upward. positive x axis continues to go right.
        xPrime = -castleCenterX + x;
        yPrime = castleCenterY - y;

        // step 2: Compute angle of the line from the origin to the given point.
        r = Math.sqrt((double) ((xPrime * xPrime) + (yPrime * yPrime))); // pythagorean theorem: euclid dist from origin to (xPrime, yPrime)
        theta = Math.acos(xPrime / r); // acos always return a number between 0 and PI -- this is annoying!!!
        if (y > castleCenterY) { // if input y coord is "below" the middle of the castle
            theta = Math.PI * 2 - theta; // then adjust theta accordingly
        }

        return theta;
    } // end of method angleWithPoint

    /**
     * Return the distance in pixels to a given point from the center of the
     * castle.
     *
     * @param x pixel x coordinate of the desired point.
     * @param y pixel y coordinate of the desired point.
     * @param castleCenterX x coordinate of the center of the castle.
     * @param castleCenterY y coordinate of the center of the castle.
     * @return The distance in pixels between the given point (x, y) and the
     * center of the castle (castleCenterX, castleCenterY). Returns -1 if
     * coordinate pair is off screen, e.g. x coordinate or y coordinate below 0,
     * or if the center of the castle is off screen, e.g. a coordinate of castle
     * below 0.
     */
    public static double distanceToPoint(int x, int y, int castleCenterX, int castleCenterY) {
        // step 0: error checking
        if (x < 0) {
            System.err.println("Error in MathUtil.distanceToPoint: given x coordinate " + x + " is negative.");
            return -1;
        }
        if (y < 0) {
            System.err.println("Error in MathUtil.distanceToPoint: given y coordinate " + y + " is negative.");
            return -1;
        }
        if (castleCenterX < 0 || castleCenterY < 0) {
            System.err.println("Error in MathUtil.distanceToPoint: Given castle center point "
                    + "(" + castleCenterX + ", " + castleCenterY + ") is outside screen.");
            return -1;
        }

        // step 0.5: local variables
        int xPrime, yPrime; // stores (x, y) after translation of the origin.
        double r; // distance (i.e. "radius") from origin to the point (xPrime, yPrime)

        // step 1: translate coordinate pair so origin is in the middle of the screen,
        // and the positive y axis goes upward. positive x axis continues to go right.
        xPrime = -castleCenterX + x;
        yPrime = castleCenterY - y;

        // step 2: Compute angle of the line from the origin to the given point.
        r = Math.sqrt((double) ((xPrime * xPrime) + (yPrime * yPrime))); // pythagorean theorem: euclid dist from origin to (xPrime, yPrime))

        return r;
    } // end of method distanceToPoint
}
