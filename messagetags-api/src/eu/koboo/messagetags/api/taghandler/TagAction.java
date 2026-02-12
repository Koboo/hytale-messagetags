package eu.koboo.messagetags.api.taghandler;

public enum TagAction {
    /**
     * i.e.
     * "<TAG>"
     */
    Open,
    /**
     * i.e.
     * "</TAG>"
     */
    Close,
    /**
     * i.e.
     * "<TAG/>"
     */
    Directive;
}
