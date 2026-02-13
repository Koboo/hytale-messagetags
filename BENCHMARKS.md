## Benchmark results don't reflect real-world scenarios!
## Only basic tags are benchmarked! (Missing support from other libraries)

# Benchmarks

This page shows results of benchmarks between the following libraries for message parsing:

### MessageTags (by Koboo)
- [GitHub](https://github.com/Koboo/hytale-messagetags)
- [Curseforge](https://www.curseforge.com/hytale/mods/messagetags)
- [ModTale](https://modtale.net/mod/messagetags-d0e70919-7bf1-4123-ae43-2c4d85d549b5)

### TinyMessage (by Zoltus)
- [GitHub](https://github.com/Zoltus/TinyMessage)
- [Curseforge](https://www.curseforge.com/hytale/mods/tinymessage)

### TaleMessage (by InsiderAnh)
- [GitHub](https://github.com/InsiderAnh/TaleMessage)
- [Curseforge](https://www.curseforge.com/hytale/mods/talemessage)

### MiniMessage (by PaperMC / kyori)
- [GitHub](https://github.com/PaperMC/adventure)
- [Docs](https://docs.papermc.io/adventure/minimessage/)

## Want to explore the results yourself?

Get the latest benchmarks results as JSON from [the benchmark module](https://github.com/Koboo/hytale-messagetags/tree/main/messagetags-benchmarks)
and explore them in the [JMH Visualizer](https://jmh.morethan.io/).

- [Credits](https://github.com/jzillmann/jmh-visualizer)

# Results

- [No-tags Messages](#no-tags-messages)
- [Simple Messages](#simple-messages)
- [Medium Messages](#medium-messages)
- [Complex Messages](#complex-messages)

## No-tags Messages

**Inputs:**
````html
Hello World
````

Single shot time / operation: _(lower is better)_
![IMG](https://i.imgur.com/qeWGXfr.png)

Average time / operation: _(lower is better)_
![IMG](https://i.imgur.com/DMfrQdk.png)

Average bytes / operation _(lower is better)_
![IMG](https://i.imgur.com/zncnMoG.png)

## Simple Messages

**Inputs:**
````html
<bold>Hello World</bold>
<color:red>Hello World</color>
<red>Hello World</red>
````

Single shot time / operation: _(lower is better)_
![IMG](https://i.imgur.com/xB9slyK.png)

Average time / operation: _(lower is better)_
![IMG](https://i.imgur.com/qgXQqEw.png)

Average bytes / operation _(lower is better)_
![IMG](https://i.imgur.com/ZVspBtH.png)

## Medium Messages

**Inputs:**
````html
<bold><italic><underline><color:red>Hello World</underline></italic></bold>
Hello <bold>world</bold>, this is a <color:yellow>yellow <italic>italic</italic></color> test.
````

Single shot time / operation: _(lower is better)_
![IMG](https://i.imgur.com/2yzCgKQ.png)

Average time / operation: _(lower is better)_
![IMG](https://i.imgur.com/ei2t6W3.png)

Average bytes / operation _(lower is better)_
![IMG](https://i.imgur.com/bXVqyPJ.png)

## Complex Messages

**Inputs:**
````html
<bold>Start</bold> of <italic>a long <color:blue>nested <underline>message</underline> with multiple</color> tags</italic> to <color:green>stress-test</color> the parser.
<bold>Aw<underline>eso</underline>me <color:#fcba03>ben<white>chm<red>ar<i>k</i> <color:dark_red>, but how<reset> fast <i>a</i>r<monospace>e</monospace>we really?
<bold><italic><underline><color:#FF0000>Hello <color:#00FF00>world <italic>and <bold>everyone <color:#0000FF>else</color></bold></italic></color></underline></italic></bold>
<color:yellow><bold>This is a very long repeated message. </bold></color><italic><color:blue>Testing multiple tags and nesting over a large amount of text. </color></italic>
<color:green><underline>Here we add more depth and style to simulate a huge chat message scenario.</underline></color>
````

Single shot time / operation: _(lower is better)_
![IMG](https://i.imgur.com/vUhI8rS.png)

Average time / operation: _(lower is better)_
![IMG](https://i.imgur.com/7uSQTwh.png)

Average bytes / operation _(lower is better)_
![IMG](https://i.imgur.com/Vl3ir0M.png)

