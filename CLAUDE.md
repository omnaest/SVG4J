# SVG4J

SVG rendering library (`org.omnaest.svg`). Provides a fluent API for building and rendering SVG documents, including a rich set of chart types. Entry point is `SVGUtils.getDrawer(width, height)`.

## Build

```cmd
mvn clean install
mvn test -Dtest=MyTestClass#myMethod
```

## Architecture

- **`SVGUtils`** — static entry point; returns an `SVGDrawer` for a given resolution or from an existing SVG input
- **`SVGDrawer`** — fluent builder; add elements, render to string/file/stream
- **`SVGChartUtils`** — secondary entry point for chart-specific builders
- **`RawSVG*` model classes** — JAXB-annotated POJOs in `org.omnaest.svg.model`; represent the raw SVG XML DOM
- **`SVG*` element classes** — higher-level wrappers in `org.omnaest.svg.elements` and `org.omnaest.svg.component`
- **Chart types** in `org.omnaest.svg.chart.types`: bar, line, radar, clock, box-map, range, red/green deviation table

The raw SVG DOM (`RawSVG*`) is parsed via CommonsJSONAndXML's `XMLHelper`. High-level elements delegate to raw elements for serialization.

## Code style

- Minimal Lombok: 1× `@Data`, 6× `@Builder` — most classes hand-written
- No logging in library code
- Factory methods: `SVGUtils.getDrawer(...)`, `SVGChartUtils.get*()`
- Charts extend `AbstractChart` / `AbstractCartesianCoordinateChart`

## Package map

| Package | What lives here |
|---|---|
| `org.omnaest.svg` | `SVGUtils`, `SVGDrawer`, `SVGChartUtils` — main entry points |
| `svg.elements` | Primitive SVG wrappers: `SVGLine`, `SVGRectangle`, `SVGText`, `SVGCircle`, etc. |
| `svg.elements.base` | Abstract base element classes |
| `svg.elements.composite` | Composite elements: `SVGTextBox`, `SVGFlowArrow`, `SVGVector` |
| `svg.component` | `SVGCompositeElement`, `SVGCompositeElementConsumer` |
| `svg.model` | `RawSVG*` JAXB model: `RawSVGRoot`, `RawSVGLine`, `RawSVGPath`, gradients, etc. |
| `svg.chart` | `Chart`, `CoordinateChart`, `RangeChart` interfaces; `AxisOptions`, `DataSeries` |
| `svg.chart.types` | Concrete chart impls: `SVGBarChart`, `SVGLineChart`, `SVGRadarChart`, `SVGClockChart`, etc. |
| `svg.chart.types.helper` | Chart rendering helpers |
| `svg.chart.common` | Shared chart abstractions |
| `svg.other` | `DisplayResolution`, `ThresholdOpacity`, `RGBAUtils` |
| `svg.internal.utils` | Internal rendering utilities |
| `svg.text` | `TextMetrics`, `FontSource`, `TextMetricsUtils` — deterministic, font-pluggable text measurement (no font bundled; test-scope only) |
| `svg.text.internal` | `AwtFontTextMetrics` (AWT-backed, real font bytes), `FixedAdvanceTextMetrics` (font-free, explicit fixed-advance) |

## Key classes

- **`SVGUtils`** — `getDrawer(width, height)` and `getDrawer(File/InputStream/String)` for parsing existing SVGs
- **`SVGDrawer`** — main builder; `addRawElement()`, `addElement()`, `render()`, `toFile()`
- **`SVGChartUtils`** — factory for all chart types
- **`RawSVGRoot`** — root of the SVG JAXB model; serialized to XML string on render
- **`ScalingSVGElementWrapper` / `TranslatingSVGElementWrapper`** — transform wrappers for embedding/scaling

## Dependencies (compile scope)

- `CommonsLangAndIO` — I/O and lang utilities
- `CommonsMath` — math utilities used in chart scaling
- `CommonsDraw` — drawing primitives
- `CommonsJSONAndXML` — XML parsing via `XMLHelper` (JAXB)

## Text metrics (`org.omnaest.svg.text`)

JDK-only (`java.awt.Font`), no new compile-scope dependency, no font bundled in the published jar.
`TextMetricsUtils.newTextMetrics(FontSource, fontSize)` loads a real TrueType font (via
`Font.createFont`) and measures with antialiasing/fractional-metrics both off for deterministic,
byte-identical advances across JVM invocations — there is no system-font fallback; a missing/bad
font throws at construction. `TextMetricsUtils.newFixedAdvanceTextMetrics(ratio, fontSize)` is an
explicit, deterministic, font-free alternative, never selected automatically. A permissively
licensed test font (Inter, SIL OFL 1.1) lives under `src/test/resources/org/omnaest/svg/text/` for
tests only. `SVGTextBox`'s own font-size guess (`text.length()/2.0`) is untouched by this package.
