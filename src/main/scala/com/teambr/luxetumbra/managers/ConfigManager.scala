package com.teambr.luxetumbra.managers

import java.io.File

import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.lib.Constants
import net.minecraftforge.common.config.Configuration

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
object ConfigManager {

    val config = new Configuration(new File(LuxEtUmbra.configLocation + File.separator + Constants.MOD_NAME + ".cfg"))

    def preInit(): Unit = {

        config.load()

        config.save()
    }
}
