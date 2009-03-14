package hr.fer.zemris.vhdllab.dao.impl;

import hr.fer.zemris.vhdllab.dao.PredefinedFilesDao;
import hr.fer.zemris.vhdllab.entity.File;
import hr.fer.zemris.vhdllab.entity.FileType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

public class PredefinedFilesDaoImpl implements PredefinedFilesDao,
        ServletContextAware {

    /**
     * Logger for this class
     */
    private static final Logger LOG = Logger
            .getLogger(PredefinedFilesDaoImpl.class);

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_LOCATION = "/WEB-INF/predefined";
    private java.io.File location;
    private ServletContext servletContext;

    private Map<String, File> files = new HashMap<String, File>();

    public void setLocation(java.io.File location) {
        this.location = location;
    }

    public java.io.File getLocation() {
        if (location == null) {
            try {
                URL url = servletContext.getResource(DEFAULT_LOCATION);
                location = new java.io.File(url.toURI());
            } catch (MalformedURLException e) {
                throw new UnhandledException(e);
            } catch (URISyntaxException e) {
                throw new UnhandledException(e);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Using predefined files location: "
                    + location.getAbsolutePath());
        }
        return location;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void initFiles() {
        for (java.io.File predefined : getLocation().listFiles()) {
            if (predefined.isFile()) {
                String contents;
                try {
                    contents = FileUtils.readFileToString(predefined,
                            DEFAULT_ENCODING);
                } catch (IOException e) {
                    throw new UnhandledException(e);
                }
                String name = predefined.getName();
                File file = new File(name, FileType.PREDEFINED, contents);
                files.put(name, file);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Loaded predefined file: " + name);
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialized with " + files.size()
                            + " predefined files.");
        }
    }

    @Override
    public List<File> getPredefinedFiles() {
        return createPredefinedFilesList();
    }

    private List<File> createPredefinedFilesList() {
        Collection<File> values = files.values();
        List<File> list = new ArrayList<File>(values.size());
        for (File f : values) {
            list.add(new File(f)); // to reduce aliasing problems
        }
        return list;
    }

    @Override
    public File findByName(String name) {
        Validate.notNull(name, "Name can't be null");
        File file = files.get(name);
        if (file != null) {
            file = new File(file); // to reduce aliasing problems
        }
        return file;
    }

}