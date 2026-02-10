# Hytale MessageTags

![IMG](https://img.shields.io/maven-central/v/eu.koboo/messagetags-api?label=messagetags-api&color=green&logo=apachemaven)
![IMG](https://img.shields.io/maven-central/v/eu.koboo/messagetags-plugin?label=messagetags-plugin&color=green&logo=apachemaven)

![IMG](https://img.shields.io/curseforge/dt/1458950?logo=curseforge&label=downloads)
![IMG](https://img.shields.io/discord/1021053609359708211?logo=discord)

MessageTags is a library and a server plugin for Hytale.
It allows easy message parsing and formatting using html-semantics,
and is similar to kyoris MiniMessage in Minecraft.

Basically, a MiniMessage clone for Hytale.

## Overview

- [Features](#features)
- [Limitations](#limitations)
- [Quick Start](#quick-start)
- [Supported Tags](#tags)
- [Supported Color Formats](#supported-color-formats)

**Links**
- [GitHub](https://github.com/Koboo/hytale-messagetags)
- [CurseForge](https://www.curseforge.com/hytale/mods/messagetags)
- [ModTale](https://modtale.net/mod/messagetags-d0e70919-7bf1-4123-ae43-2c4d85d549b5)
- [MavenCentral (API)](https://mvnrepository.com/artifact/eu.koboo/messagetags-api)
- [MavenCentral (Plugin)](https://mvnrepository.com/artifact/eu.koboo/messagetags-plugin)
- [Discord](https://discord.koboo.eu/)

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

## Limitations

- Dynamic colors cannot be closed
- Gradients do not apply to translations
- Invalid tags are rendered as raw text

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

## Supported Tags

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

Applies the color to the text.

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

- `COLOR` — Any valid color ([Support Color Format](#supported-color-formats))

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

- `COLOR` — Any valid color ([Support Color Format](#supported-color-formats))

## Color Transitions

Adds the transition to the following text.

#### Tags:

````html
<transition:COLOR:COLOR:COLOR:PHASE>Hello World!</transition>
<trnsn:COLOR:COLOR:COLOR:PHASE>Hello World!</trnsn>
````

#### Arguments:

- `COLOR` — Any valid color format ([Support Color Format](#supported-color-formats))
- `PHASE` — A number between 0 and 1

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