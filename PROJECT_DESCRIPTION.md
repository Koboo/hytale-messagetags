# Hytale MessageTags

![IMG](https://img.shields.io/maven-central/v/eu.koboo/messagetags-api?label=messagetags-api&color=green&logo=apachemaven)
![IMG]_(https://img.shields.io/maven-central/v/eu.koboo/messagetags-plugin?label=messagetags-plugin&color=green&logo=apachemaven)_

![IMG](https://img.shields.io/curseforge/dt/1458950?logo=curseforge&label=downloads)
![IMG](https://img.shields.io/discord/1021053609359708211?logo=discord)

MessageTags is a library and a server plugin for Hytale.
It allows easy message parsing and formatting using html-semantics,
and is similar to kyoris MiniMessage in Minecraft.

Basically, a MiniMessage clone for Hytale.

## Links
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
- Add placeholders (raw, embedded, parsable)
- Multiplatform support:
  - Standalone plugin
  - Maven/Gradle library (shadable)

**Performance**
- Zero-regex parsing
- Minimal object allocation
- Single-pass parsing

**Limitations**
- Tags using dynamic colors can't be closed directly and need to be closed a reset tag
- Gradients do not apply to translations
- The client parses translations, so styling within translations doesn't work (for now)
- Invalid tags are rendered as raw text, no error is shown or thrown

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