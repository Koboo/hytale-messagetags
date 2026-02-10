# Hytale MessageTags

MessageTags is a library and a server plugin for Hytale.
It allows easy message parsing and formatting using html-semantics,
and is similar to kyoris MiniMessage in Minecraft.

_____________________________________

## Overview

- [Features](#features)
- [Limitations](#limitations)
- Tags:
  - [Bold](#bold)
  - [Underlined](#underlined)
  - [Italic](#italic)
  - [Color](#color)
  - [Line breaks](#line-breaks)
  - [Link](#link)
  - [Monospace](#monospace)
  - [Reset](#reset)
  - [Translation](#translation)
  - [Gradients](#color-gradients)
  - [Transitions](#color-transitions)
- [Supported Color Formats](#supported-color-formats)

_____________________________________

## Features

### Styling and formatting

- Colors
- Color gradients
- Color transitions
- Embedded links
- Asset translations
- Text formatting
- Line Breaks
- Style and format resets
- Unlimited nesting

### Extensibility

- Stripping support (Remove styles and formatting)
- Create own ``MessageParser``
- Create own ``NamedColor``
- Implement own ``TagHandler``
- Multiplatform support:
  - Standalone plugin
  - Maven library

### Performance

- Zero-regex parsing
- Minimal object allocation
- Single-pass parsing

_____________________________________

## Limitations

- Dynamic colors cannot be closed
- Gradients do not apply to translations
- Invalid tags are rendered as raw text

_____________________________________

## Quick Start

**Add dependency**
````kotlin
dependencies {
    implementation("eu.koboo:hytale-messagetags:1.0.0")
}
````

**Parse message**
````java
Message message = MessageTags.parse("<bold><red>Hello World!</red></bold>");
// Output: bold + red "Hello World!"
````

**Strip message**
````java
Message message = MessageTags.strip("<bold><red>Hello World!</red></bold>");
// Output: "Hello World!"
````

_____________________________________

## Bold

**Preview: This text is bold**

#### Tags:

````html
<bold>This text is bold</bold>
<b>This text is bold</b>
````

## Underlined

<u>Preview: This text is underlined</u>

#### Tags:

````html
<underlined>This text is underlined</underlined>
<underline>This text is underlined</underline>
<ul>This text is underlined</ul>
````

## Italic

_Preview: This text is italic_

#### Tags:

````html
<italic>This text is italic</italic>
<i>This text is italic</i>
<em>This text is italic</em>
````

## Color

<span style="color:yellow">Preview: This text is colored</span>

#### Tags:

````html
<color:COLOR>This text is colored</color>
<colour:COLOR>This text is colored</color>
<c:COLOR>This text is colored</color>
````

````html
<COLOR>This text is colored
````

#### Arguments:

- `COLOR` — Any valid color format

#### Notes:

- Dynamic colors have no closing tag
- Close dynamic colors by using the ``<r/>`` tag or ``<reset/>``

## Line breaks

Inserts a new line break using `\n`

#### Tags:

````html
<newline/>
<linebreak/>
<br/>
````

## Link

[Preview: This text has an embedded link](https://github.com/Koboo/README/#link)

#### Tags:

````html
<link:URL>This text is clickable</link>
<url:URL>This text is clickable</url>
<uri:URL>This text is clickable</uri>
````

#### Arguments:

- `URL` — Any valid URL

## Monospace

``Preview: This text is monospaced``

#### Tags:

````html
<monospaced>This text is monospaced</monospaced>
<monospace>This text is monospaced</monospace>
<mono>This text is monospaced</mono>
<ms>This text is monospaced</ms>
````

## Reset

Closes any unclosed styles and tags.

#### Tags:

````html
<reset/>
<r/>
````

## Translation

Inserts the message from the resource files translations based on the given translation key.

#### Tags:

````html
<translation:TRANSLATION_KEY/>
<lang:TRANSLATION_KEY/>
````

#### Arguments:

- `TRANSLATION_KEY` — Any valid resource file translation

#### Notes:

- Translations don't support gradients because the actual text gets parsed by the client.

## Color Gradients

Adds the gradient to the following text.

#### Tags:

````html
<gradient:COLOR:COLOR:COLOR>Hello World!</gradient>
<grnt:COLOR:COLOR:COLOR>Hello World!</grnt>
<grad:COLOR:COLOR:COLOR>Hello World!</grad>
````

#### Arguments:

- `COLOR` — Any valid color format

## Color Transitions

Adds the transition to the following text.

#### Tags:

````html
<transition:COLOR:COLOR:COLOR:PHASE>Hello World!</transition>
<trnsn:COLOR:COLOR:COLOR:PHASE>Hello World!</trnsn>
````

#### Arguments:

- `COLOR` — Any valid color format
- `PHASE` — A number between 0 and 1

_____________________________________

## Supported Color Formats

Here is a list of all supported color formats with an example.

**Hexadecimal:**
- `#ffffff`

**Named colors:**
- `white`

**Color codes:**
- `&f`
- `§f`

#### Notes:

- All formats are functionally equivalent and can be mixed freely
- You can register your own ``NamedColor``
- You can override existing ``NamedColor``
- Your custom ``NamedColor`` also support color codes using `§` and `&`