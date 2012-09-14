/*******************************************************************************
 * Copyright (c) 2002, 2010 Innoopract Informationssysteme GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Innoopract Informationssysteme GmbH - initial API and implementation
 *     EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.rap.tools;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * <p>This tool creates the content of a simple config.ini file.
 * Run this after the ANT build scripts (webappBuilder.xml and the 
 * pde.exportFeatures) have finished. After that replace the content
 * of the config.ini file in the build.</p> 
 * 
 * <p>Note: this is not meant to be a high end deployment tool or the only
 * possibility of how the content of your config.ini should look like if you
 * are creating a RAP WAR. This should simplify the task to get
 * a minimalistic runtime configuration that works...</p>
 * 
 * <p> THis class is no longer needed, as the config.ini is statically configured in the templates directory
 */
public class ConfigIniCreator {
  
  private static final String FRAMEWORK_BUNDLE = "org.eclipse.osgi_";
  private static final String EXTENSION_BUNDLE
    = "org.eclipse.equinox.servletbridge.extensionbundle";

  public static void main( final String[] args ) throws IOException {
	Properties scaProps = new Properties();
	scaProps.load(ConfigIniCreator.class.getResourceAsStream("/sca.properties"));
	/*
	 * Define property plugins.dir in sca.properties, as the absolute path to the build directory where the plug-ins are exported
	 * by task pdeExportFeratures in ant script webappBuklder.xml
	 */
	File file = new File(scaProps.getProperty("plugins.dir"));
    ////////////////////////////////////////////////////////////////////////////
    
    String[] list = file.list();
    StringBuffer buffer = new StringBuffer();
    buffer.append( "#Eclipse Runtime Configuration File\n" );
    buffer.append( "osgi.bundles=" );
    for( int i = 0; i < list.length; i++ ) {
      String fileName = list[ i ];
      if(    fileName.endsWith( ".jar" ) 
          && !fileName.startsWith( FRAMEWORK_BUNDLE ) )
      {
        // Remove version number
        int underscorePos = fileName.indexOf( "_" );
        String bundleName = fileName.substring( 0, underscorePos );
        // Append bundle name
        buffer.append( "  " );
        buffer.append( bundleName );
        if( !list[ i ].startsWith( "org.eclipse.rap.rwt.q07_" ) ) {
          if( list[ i ].startsWith( "org.eclipse.equinox.common_" ) ) {
            buffer.append( "@2:start" );
          } else {
            buffer.append( "@start" );
          }
        }
        buffer.append( ",\\\n" );
        if( i + 1 >= list.length ) {
          buffer.append( "  " );
          buffer.append( EXTENSION_BUNDLE );
        }
      }
    }
    buffer.append( "\n" );
    buffer.append( "osgi.bundles.defaultStartLevel=4\n" );
    // write the content to the console
    System.out.print( buffer );
  }
}
