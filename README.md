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

- [Features](#-features)
- [Quick Start](#-quick-start)
- [Tag Format](#tag-format)
- [Builtin Tags](#builtin-tags)
- [Supported Color Formats](#-supported-color-formats)
- [Named Colors](#named-and-legacy-colors)
- [MiniMessage Online Editor](https://webui.advntr.dev/)
- [Benchmarks](https://github.com/Koboo/hytale-messagetags/BENCHMARKS.md)

**Links**
- [GitHub](https://github.com/Koboo/hytale-messagetags)
- [CurseForge](https://www.curseforge.com/hytale/mods/messagetags)
- [ModTale](https://modtale.net/mod/messagetags-d0e70919-7bf1-4123-ae43-2c4d85d549b5)
- [MavenCentral (API)](https://central.sonatype.com/artifact/eu.koboo/messagetags-api)
- [MavenCentral (Plugin)](https://central.sonatype.com/artifact/eu.koboo/messagetags-plugin)
- [Discord](https://discord.koboo.eu/)

## 📑 Features

**Styling and formatting**
- Colors (Hexadecimal and named colors)
- Color gradients
- Color transitions
- Embedded links
- Asset translations
- Text formatting
- Line Breaks
- Style and format resets
- Inline legacy color codes

**Extensibility**
- Stripping support (Remove styles and formatting)
- No exception throwing
- Create own ``MessageParser``
- Create own ``NamedColor``
- Implement own ``TagHandler``
- Multiplatform support:
  - Standalone plugin
  - Maven/Gradle library (shadable)

**Performance**
- Zero-regex parsing
- Minimal object allocation
- Single-pass parsing

**Limitations**
- Dynamic colors cannot be closed
- Gradients do not apply to translations
- Invalid tags are rendered as raw text

## 🐇 Quick Start

**Add dependency**
````kotlin
dependencies {
    // To include the library in your plugin:
    implementation("eu.koboo:messagetags-api:1.2.0")

    // Or to use the standalone plugin:
    compileOnly("eu.koboo:messagetags-plugin:1.2.0")
}
````

**Add manifest dependency (plugin-only)**
````json
{
    "Dependencies": {
        "Koboo:MessageTags": "*"
    }
}
````

**Parse text to Message**
````java
Message message = MessageTags.parse("<bold><red>Hello World!</red></bold>");
// Output: bold + red "Hello World!"
````

**Strip text to Message**
````java
Message message = MessageTags.strip("<bold><red>Hello World!</red></bold>");
// Output: "Hello World!"
````

## Tag Format

Every tag follows the same format. There are only three tag types:

**Opening a new tag**
````html
<TAG_NAME:ARGUMENT_1:ARGUMENT_2>
````

**Closing a previously opened tag**
````html
</TAG_NAME>
````

**Insert a directive tag** (A tag with "closes" itself)
````html
<TAG_NAME/>
````

## Builtin Tags

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

**Preview: Hello World!**

![IMG](https://i.imgur.com/rU1fuLj.png)

#### Tags:

````html
<strong>Hello World!</strong>
<bold>Hello World!</bold>
<b>Hello World!</b>
````

## Underlined

<u>Preview: Hello World!</u>

![IMG](https://i.imgur.com/U6XmuoD.png)

#### Tags:

````html
<underlined>Hello World!</underlined>
<underline>Hello World!</underline>
<ul>Hello World!</ul>
````

## Italic

_Preview: Hello World!_

![IMG](https://i.imgur.com/YYa9ca1.png)

#### Tags:

````html
<italic>Hello World!</italic>
<em>Hello World!</em>
<i>Hello World!</i>
````

## Monospace

``Preview: This text is monospaced``

![IMG](https://i.imgur.com/9IUGbC6.png)

#### Tags:

````html
<monospaced>Hello World!</monospaced>
<monospace>Hello World!</monospace>
<mono>Hello World!</mono>
````

## Color

Applies the color to the text.

![IMG](https://i.imgur.com/5Yyyjo6.png)

#### Tags:

````html
<color:COLOR>Hello World!</color>
<colour:COLOR>Hello World!</colour>
<c:COLOR>Hello World!</c>
````

````html
<COLOR>Hello World!
````

#### Arguments:

- `COLOR` — Any valid color ([Support Color Format](#-supported-color-formats))

#### Notes:

- Dynamic colors have no closing tag
- Close dynamic colors by using the ``<r/>`` tag or ``<reset/>``

## Line breaks

Inserts a new line break using `\n`

![IMG](https://i.imgur.com/0jpsgDy.png)

#### Tags:

````html
<linebreak/>
<br/>
````

## Link

[Preview: This text has an embedded link](https://github.com/Koboo/hytale-messagetags)

![IMG](https://i.imgur.com/vnAX7Yo.png)

#### Tags:

````html
<link:URL>Hello World!</link>
<url:URL>Hello World!</url>
````

#### Arguments:

- `URL` — Any valid URL

## Reset

Closes any unclosed styles and tags.

![IMG](https://i.imgur.com/OADPDqk.png)

#### Tags:

````html
<reset/>
<r/>
````

## Translation

Inserts the message from the resource files translations based on the given translation key.

![IMG](https://i.imgur.com/gz8eVYG.png)

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

![IMG](https://i.imgur.com/GmgzGz3.png)

#### Tags:

````html
<gradient:COLOR:COLOR:COLOR>Hello World!</gradient>
<grnt:COLOR:COLOR:COLOR>Hello World!</grnt>
````

#### Arguments:

- `COLOR` — Any valid color ([Support Color Format](#-supported-color-formats))

## Color Transitions

Adds the transition to the following text.

![IMG](https://i.imgur.com/exwSw76.png)

#### Tags:

````html
<transition:COLOR:COLOR:COLOR:PHASE>Hello World!</transition>
<trnsn:COLOR:COLOR:COLOR:PHASE>Hello World!</trnsn>
````

#### Arguments:

- `COLOR` — Any valid color format ([Support Color Format](#-supported-color-formats))
- `PHASE` — A number between 0 and 1

## 🎨 Supported Color Formats

Here is a list of all supported color formats with an example.

**Hexadecimal:**
- `#ffffff`

**Named colors:**
- `white`

#### Notes:

- All formats are functionally equivalent and can be mixed freely
- You can register your own ``NamedColor``
- You can override existing ``NamedColor``

## Named and legacy colors

The builtin named color represents the legacy color codes from Minecraft.

| Name        | ColorCode | Hexcode |
|-------------|-----------|---------|
| Black       | 0         | #000000 |
| DarkBlue    | 1         | #0000AA |
| DarkGreen   | 2         | #00AA00 |
| DarkAqua    | 3         | #00AAAA |
| DarkRed     | 4         | #AA0000 |
| DarkPurple  | 5         | #AA00AA |
| Gold        | 6         | #FFAA00 |
| Gray        | 7         | #AAAAAA |
| DarkGray    | 8         | #555555 |
| Blue        | 9         | #5555FF |
| Green       | a         | #55FF55 |
| Aqua        | b         | #55FFFF |
| Red         | c         | #FF5555 |
| LightPurple | d         | #FF55FF |
| Yellow      | e         | #FFFF55 |
| White       | f         | #FFFFFF |

You can easily inline legacy color codes using the symbols ``§`` and ``&``.

````html
<bold>This is &6my §a§mawesome &rmessage, which uses legacy color codes!</bold>
````

![IMG](https://i.imgur.com/xohJ1tL.png)

## MiniMessage Online Editor

There is also a web editor for MiniMessage, which displays your preview in a Minecraft-style.
You can edit your texts there and just parse them using this library.

[MiniMessage Web Editor](https://webui.advntr.dev/)


## Benchmarks

You can check out benchmarks for this library [here](https://github.com/Koboo/hytale-messagetags/BENCHMARKS.md).