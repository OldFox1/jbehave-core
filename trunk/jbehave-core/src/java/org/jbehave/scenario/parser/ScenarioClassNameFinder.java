package org.jbehave.scenario.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;

/**
 * Finds scenario class names from a base directory
 * 
 * @author Mauro Talevi
 */
public class ScenarioClassNameFinder {

    /**
     * Scanner used to list paths
     */
    private DirectoryScanner scanner = new DirectoryScanner();

    /**
     * Lists scenario class names from a base directory, allowing for includes/excludes
     * 
     * @param basedir the base directory path
     * @param rootPath the root path prefixed to all paths found, or
     *            <code>null</code> if none
     * @param includes the List of include patterns, or <code>null</code> if
     *            none
     * @param excludes the List of exclude patterns, or <code>null</code> if
     *            none
     * @return A List of paths
     */
    public List<String> listScenarioClassNames(String basedir, String rootPath, List<String> includes,
            List<String> excludes) {
        List<String> classNames = new ArrayList<String>();
        for (String path : listPaths(basedir, rootPath, includes, excludes)) {
            classNames.add(classNameFor(path));
        }
        return classNames;
    }

    private String classNameFor(String path) {
        String className = path.substring(0, path.indexOf(".java"));
        return className.replaceAll("/", "\\.");
    }

    private List<String> listPaths(String basedir, String rootPath, List<String> includes, List<String> excludes) {
        scanner.setBasedir(basedir);
        if (includes != null) {
            scanner.setIncludes(includes.toArray(new String[includes.size()]));
        }
        if (excludes != null) {
            scanner.setExcludes(excludes.toArray(new String[excludes.size()]));
        }
        scanner.scan();
        List<String> paths = new ArrayList<String>();
        String basePath = (rootPath != null ? rootPath + "/" : "");
        for (String relativePath : scanner.getIncludedFiles()) {
            String path = basePath + relativePath;
            paths.add(path);
        }
        return paths;
    }

}
