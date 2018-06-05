<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	S<xsl:output method = "xml"/>
	
	<xsl:template match="/">
		<!-- TODO: Auto-generated template -->
		<PartnerCustomer>
			<xsl:for-each>
				<customer>
					<customerId><xsl:value-of select = "@Id" /></customerId>
					<firstName><xsl:value-of select = "firstName"/></firstName>
					<lastName><xsl:value-of select = "lastName" /></lastName>
					<email><xsl:value-of select = "email" /></email>
				</customer>
			</xsl:for-each>
		</PartnerCustomer>
	</xsl:template>
</xsl:stylesheet>