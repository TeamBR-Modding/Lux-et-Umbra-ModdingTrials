package com.teambr.projectdn.managers

import java.io.File

import com.teambr.projectdn.ProjectDN
import com.teambr.projectdn.lib.Constants
import net.minecraftforge.common.config.Configuration

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
object ConfigManager {

    val config = new Configuration(new File(ProjectDN.configLocation + File.separator + Constants.MOD_NAME + ".cfg"))

    def preInit(): Unit = {

        config.load()

        config.save()
    }
}
