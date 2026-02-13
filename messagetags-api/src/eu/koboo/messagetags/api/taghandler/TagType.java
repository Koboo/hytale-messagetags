package eu.koboo.messagetags.api.taghandler;

public enum TagType {
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
