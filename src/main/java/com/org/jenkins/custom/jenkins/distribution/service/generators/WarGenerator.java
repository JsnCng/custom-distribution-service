package com.org.jenkins.custom.jenkins.distribution.service.generators;

import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Logger;

@SuppressWarnings("PMD.LawOfDemeter")
public class WarGenerator {

    private final static Logger LOGGER = Logger.getLogger(WarGenerator.class.getName());


    public File generateWAR(final String configuration) throws IOException, InterruptedException {
        LOGGER.info("Generating War File");
        final Config cfg;
        final File packagerFile = File.createTempFile("packager-config", ".yml");
            final byte[] buf = configuration.getBytes(StandardCharsets.UTF_8);
            Files.write(packagerFile.toPath(), buf);
            cfg = Config.loadConfig(packagerFile);
            cfg.buildSettings.setVersion(cfg.buildSettings.getVersion());
            cfg.buildSettings.setInstallArtifacts(true);
            new Builder(cfg).build();
            LOGGER.info("Cleaning up temporary directory");
            packagerFile.deleteOnExit();
            return cfg.getOutputWar();
    }

}
