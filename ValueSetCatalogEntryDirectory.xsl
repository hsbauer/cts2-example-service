<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:core="http://schema.omg.org/spec/CTS2/1.0/Core"
    xmlns:valueset="http://schema.omg.org/spec/CTS2/1.0/ValueSet"
    >
    <xsl:template match="/">
        <html>
            <body>
                <h2><xsl:value-of select="valueset:ValueSetCatalogEntryDirectory/@complete"/></h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Description</th>
                        <th>Link</th>
                    </tr>
                    <xsl:for-each select="valueset:ValueSetCatalogEntryDirectory/valueset:entry">
                        <tr>
                            <td><xsl:value-of select="@formalName"/></td>
                            <td><a href="{@href}/resolution"><xsl:value-of select="@resourceName"/></a></td> 
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>