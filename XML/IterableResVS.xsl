<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:core="http://schema.omg.org/spec/CTS2/1.0/Core"
    xmlns:valuesetdef="http://schema.omg.org/spec/CTS2/1.0/ValueSetDefinition">
    <xsl:template match="/">
        <html>
            <body>
                <h2>ResolvedValueSet</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Source</th>
                        <th>Unique Identifier</th>
                        <th>Description</th>
                    </tr>
                    <xsl:for-each select="valuesetdef:IteratableResolvedValueSet/valuesetdef:entry">
                        <tr>
                            <td><xsl:value-of select="core:namespace"/></td>
                            <td><xsl:value-of select="core:name"/></td> 
                            <td><xsl:value-of select="core:designation"/></td> 
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>