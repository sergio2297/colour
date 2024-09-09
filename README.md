# Java Colour library
![language](https://img.shields.io/badge/language-java%2021-red)
![coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)<br>
The library provides classes to easily handle colours in Java by being able to accept several colour codifications and
conversions between them.

## Index
- [Description](#description)
- [How it works?](#how-it-works)
  - [Some ColourCode examples](#some-colourcode-examples)
  - [Convert a ColourCode to another](#convert-a-colourcode-to-another)
  - [Using Colour](#using-colour)
  - [Dealing with transparency](#dealing-with-transparency)
- [Notes](#notes)

## Description
The library provides classes to handle colours in Java. It works with <b>several colour codifications</b> like RGB, HSL, CMYK...
and it provides <b>conversion algorithms</b> that convert any colour code to another. The basics are:
<ul>
<li><code><b>ColourCode</b></code>: representation of a colour in a specific codification, e.g., RGB or HSV.</li>
<li><code><b>ColourCodeConversion</b></code>: algorithm that converts a colour from codification A to B directly.</li>
<li><code><b>ColourCodeConverter</b></code>: after specifying the available conversion algorithm list 
(<code>ColourCodeConversion</code>), it can convert a colour from one codification to another directly or by using 
some middle steps.
<li><code><b>Colour</b></code>: it can be created from any <code>ColourCode</code> and it's the representation of a 
color as a concept. It means that two Colours are equal iff they represent the same colour, no matter what codification
they use.</li>
</ul>

## How it works?

### Some ColourCode examples
Each <code>ColourCode</code> has some constructor that allow creating them easily.

```java
RgbCode redRgb = new RgbCode(255, 0, 0);
HexCode greenHex = new HexCode("#00FF00");
HslCode blueHsl = new HslCode(240, 100, 50);
CmykCode greyCmyk = new CmykCode(0, 0, 0, 50);
```

All implemented <code>AcceptedByCssColourCode</code> have also a constructor that receives the colour's CSS representation.

```java
RgbCode rgbFromCss = new RgbCode("rgba(63, 127, 88, 0.18)");
HslCode hslFromCss = new HslCode("hsl(3, 9%, 88%)");
```

### Convert a ColourCode to another
It's possible to convert one colour code to another by using directly the <code>ColourCodeConversion</code> needed:

```java
ColourCodeConversion<RgbCode, HexCode> rgb2Hex = new RgbToHexCodeConversion();

RgbCode redRgb = new RgbCode(255, 0, 0);
HexCode redHex = rgb2Hex.convert(redRgb);
```

But this isn't the best way to achieve it. Instead of instantiate the <code>ColourCodeConversion</code>, it's better to
use a <code>ColourCodeConverter</code>.

```java
ColourCodeConverter converter = new ColourCodeConverter();

RgbCode redRgb = new RgbCode(255, 0, 0);
HexCode redHex = converter.convert(redRgb, HexCode.class); // Converts RGB to Hex
CmykCode redCmyk = converter.convert(redHex, CmykCode.class); // Converts Hex to CMYK
HslCode redHsl = converter.convert(redCmyk, HslCode.class); // Converts CMYK to HSL
```

The default converter doesn't offer a specific conversion algorithm between each pair of colour code. But if it has 
a <code>ColourCodeConversion</code> that converts A to B, and another that converts B to C, it will be able to convert a
colour codified in A to C.

In any case, you can always create your own conversion algorithm A to C and add it to the available conversions of the 
<code>ColourCodeConverter</code>, that way you can speed up the conversion you need.

<i>The default converter deals with all defined colour codes in this library.</i>

### Using Colour
You can instantiate a <code>Colour</code> from any <code>ColourCode</code> implementation.

```java
// Instantiate a new Colour by passing its codification
RgbCode redRgb = new RgbCode(255, 0, 0);
Colour redFromRgb = new Colour(redRgb);

// Create it directly using ColourCode.toColour()
Colour redFromHex = new HexCode("FF0000").toColour();
```

Once you have a <code>Colour</code>, you can get its representation in any <code>ColourCode</code>. <i>(It's another way to
convert colour codifications)</i>

```java
HexCode redHex = redFromRgb.as(HexCode.class);
```

When a Colour is created, it will reference a default <code>ColourCodeConverter</code>, thanks to it is possible to get
different codifications of one colour. You can always create it passing your custom converter.

To work with codifications extracted directly from CSS, you create a <code>Colour</code> passing the String codification
directly to a <code>WebColour</code>.

```java
String css = "#FF0AFB";
// Assertion is unnecessary, but call WebColour.from() with a not valid css code will throw an IllegalArgumentException
if(WebColour.isCssColourCode(css)) {
    Colour colour = WebColour.from(css);
}
```

### Dealing with transparency

Colours used in web has transparency. If transparency isn't explicitly indicated, it will act as full opacity. E.g.: 
<code>rgba(0, 0, 0, 50%)</code> represents a black colour with 50% opacity.

The library supports this characteristic too. And conversions between colours will transfer opacity, but only and only
if both work with opacity. 

## Notes
<ul>
<li>New <code>ColourCode</code> will be added in the future: HWB, NCol, HTML named colours and PMS (for the moment).</li>
<li>At this moment, the CSS constructor of the implemented <code>AcceptedByCssColourCode</code> will throw an exception 
with some codifications supported by CSS. E.g.: it's not possible to use rad or deg <code>hsl(150deg 30% 60%)</code></li>
</ul>
