package eu.koboo.messagetags.api;

public final class StringCursor {

    // original string
    private final String input;

    // inclusive
    private final int startPos;
    // exclusive
    private final int endPos;

    // relative to startPos
    private int currentPos;

    public StringCursor(String input) {
        this(input, 0, input.length());
    }

    public StringCursor(String input, int startPos, int endPos) {
        if (input == null) {
            throw new IllegalArgumentException("Input string must not be null.");
        }

        if (startPos < 0) {
            throw new IndexOutOfBoundsException("startPos cannot be negative: " + startPos);
        }

        if (endPos < startPos) {
            throw new IndexOutOfBoundsException("endPos cannot be smaller than startPos. startPos="
                    + startPos + ", endPos=" + endPos);
        }

        if (endPos > input.length()) {
            throw new IndexOutOfBoundsException("endPos exceeds input length. endPos="
                    + endPos + ", length=" + input.length());
        }

        this.input = input;
        this.startPos = startPos;
        this.endPos = endPos;
        this.currentPos = 0;
    }

    public int length() {
        return endPos - startPos;
    }

    public int currentPosition() {
        return currentPos;
    }

    public int absolutePosition() {
        return startPos + currentPos;
    }

    public void setPosition(int pos) {
        if (pos < 0 || pos > length()) {
            throw new IndexOutOfBoundsException(
                    "Position out of bounds. pos=" + pos + ", allowed range=0.." + length()
            );
        }

        this.currentPos = pos;
    }

    public boolean hasNext() {
        return currentPos < length();
    }

    public boolean hasPrevious() {
        return currentPos > 0;
    }

    public char currentChar() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException(
                    "No current character available. Cursor at position " + currentPos
            );
        }

        return input.charAt(startPos + currentPos);
    }

    public char nextChar() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException(
                    "Cannot advance beyond end of view. Cursor at position " + currentPos
            );
        }

        char c = input.charAt(startPos + currentPos);
        currentPos++;
        return c;
    }

    public char previousChar() {
        if (!hasPrevious()) {
            throw new IndexOutOfBoundsException(
                "Cannot move before start of view. Cursor at position " + currentPos
            );
        }

        currentPos--;
        return input.charAt(startPos + currentPos);
    }

    public char peekNext(int offset) {
        int abs = startPos + currentPos + offset;

        if (abs < startPos || abs >= endPos) {
            throw new IndexOutOfBoundsException(
                    "Peek offset out of bounds. offset=" + offset +
                    ", absoluteIndex=" + abs +
                    ", allowed range=" + startPos + ".." + (endPos - 1)
            );
        }

        return input.charAt(abs);
    }

    public char peekPrevious(int offset) {
        if (offset <= 0) {
            throw new IllegalArgumentException("Offset must be positive: " + offset);
        }

        int abs = startPos + currentPos - offset;
        if (abs < startPos) {
            throw new IndexOutOfBoundsException(
                "Peek previous out of bounds. Offset=" + offset +
                    ", absoluteIndex=" + abs +
                    ", allowed range=" + startPos + ".." + (startPos + currentPos - 1)
            );
        }

        return input.charAt(abs);
    }

    public boolean charEquals(int position, char expected) {
        int abs = startPos + position;
        if(abs < startPos || abs >= endPos) {
            throw new IndexOutOfBoundsException(
                "Peek offset out of bounds. position=" + position +
                    ", absoluteIndex=" + abs +
                    ", allowed range=" + startPos + ".." + (endPos - 1)
            );
        }
        return input.charAt(abs) == expected;
    }

    private void assertRelativeOffset(int relativeStart, int relativeEnd) {
        if (relativeStart < 0) {
            throw new IndexOutOfBoundsException("relativeStart cannot be negative: " + relativeStart);
        }

        if (relativeEnd < relativeStart) {
            throw new IndexOutOfBoundsException(
                "relativeEnd cannot be smaller than relativeStart. relativeStart=" + relativeStart + ", relativeEnd=" + relativeEnd
            );
        }

        if (relativeEnd > length()) {
            throw new IndexOutOfBoundsException(
                "relativeEnd exceeds view length. relativeEnd=" + relativeEnd + ", viewLength=" + length()
            );
        }
    }

    public StringCursor subCursor(int relativeStart, int relativeEnd) {
        assertRelativeOffset(relativeStart, relativeEnd);
        return new StringCursor(
                input,
                startPos + relativeStart,
                startPos + relativeEnd
        );
    }

    public String subString(int relativeStart, int relativeEnd) {
        assertRelativeOffset(relativeStart, relativeEnd);
        if(relativeStart == relativeEnd) {
            return null;
        }
        int beginPos = startPos + relativeStart;
        int endPos = startPos + relativeEnd;
        if(beginPos == endPos) {
            return null;
        }
        return input.substring(beginPos, endPos);
    }

    public String subString() {
        return input.substring(startPos, endPos);
    }

    private void assertStringNotNull(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String must not be null.");
        }
    }

    public boolean startsWith(String string) {
        assertStringNotNull(string);

        int len = string.length();

        if (currentPos + len > length()) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (input.charAt(startPos + currentPos + i) != string.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public boolean endsWith(String string) {
        assertStringNotNull(string);

        int len = string.length();

        if (currentPos - len < 0) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (input.charAt(startPos + currentPos - len + i) != string.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public boolean contains(String string) {
        assertStringNotNull(string);

        int idx = input.indexOf(string, startPos + currentPos);

        if (idx == -1) {
            return false;
        }

        return idx + string.length() <= endPos;
    }

    public boolean startsWithChar(char character) {
        if (!hasNext()) {
            return false;
        }

        return currentChar() == character;
    }

    public boolean endsWithChar(char character) {
        if (currentPos <= 0) {
            return false;
        }

        return input.charAt(startPos + currentPos - 1) == character;
    }

    public boolean containsChar(char character) {
        int idx = input.indexOf(character, startPos + currentPos);

        if (idx == -1) {
            return false;
        }

        return idx < endPos;
    }

    public int firstIndexOf(char character) {
        int idx = input.indexOf(character, startPos + currentPos);

        if (idx == -1 || idx >= endPos) {
            return -1;
        }

        return idx - startPos;
    }

    public int lastIndexOf(char character) {
        int idx = input.lastIndexOf(character, endPos - 1);

        if (idx == -1 || idx < startPos) {
            return -1;
        }

        return idx - startPos;
    }
}