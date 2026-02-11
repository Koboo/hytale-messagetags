package eu.koboo.messagetags.api.taghandler;

public enum TagAction {
    /**
     * i.e.
     * "<TAG>"
     */
    Open(1),
    /**
     * i.e.
     * "</TAG>"
     */
    Close(2),
    /**
     * i.e.
     * "<TAG/>"
     */
    Directive(1);

    public final int namePosPadding;

    TagAction(int namePosPadding) {
        this.namePosPadding = namePosPadding;
    }
}
