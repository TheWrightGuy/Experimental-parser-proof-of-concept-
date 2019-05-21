# Universal Documentation Generator (UDG)
**summary:** A program that reads in a file path, and spits out documentation for any language in the form of a Universal Documnetation File ( \*.udf or "UDF" ).

## Design

The process of generating UDFs is broken down into 5 steps.
1. **Identification** - each file type must be identified to properly detect documnetation comments.
2. **Matching** - cross reference file type with database. If filetype is unknown, ask user for symbol representing a single line comment.
3. **Analyzing** - Reads through the target file, extracting the documentation data embeded within.
4. **Processing** - Converts the data into tokens that represent the target file's documentation.
5. **Generation** - The tokens are transformed into (both?) a HTML file and a UDF for user's convinience.



















###### Post Script Comments
***yes, we're fully aware we document our code javadoc style. Yes, it's ironic. More people know it though, so there.***
