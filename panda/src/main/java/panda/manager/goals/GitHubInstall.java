/*
 * Copyright (c) 2021 dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package panda.manager.goals;

import panda.manager.Dependency;
import panda.manager.PackageInfo;
import panda.manager.PackageManager;
import panda.utilities.FileUtils;
import panda.utilities.IOUtils;
import panda.utilities.ZipUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.zip.ZipInputStream;

final class GitHubInstall implements CustomInstall {

    private final PackageManager packageManager;
    private final Dependency dependency;

    GitHubInstall(PackageManager packageManager, Dependency dependency) {
        this.packageManager = packageManager;
        this.dependency = dependency;
    }

    @Override
    public List<Dependency> install(BiConsumer<InstallStatus, Dependency> resultConsumer, File ownerDirectory, File packageInfoFile) throws Exception {
        File packageDirectory = new File(ownerDirectory, dependency.getName());
        FileUtils.delete(packageDirectory);

        String address = "https://github.com/" + dependency.getAuthor() + "/" + dependency.getName() + "/archive/" + dependency.getVersion() + ".zip";
        BufferedInputStream in = null;

        try {
            in = new BufferedInputStream(new URL(address).openStream());
            ZipInputStream zipStream = new ZipInputStream(in);
            ZipUtils.extract(zipStream, ownerDirectory);

            File extractedModule = new File(ownerDirectory, dependency.getName() + "-" + dependency.getVersion());
            extractedModule.renameTo(packageDirectory);
            resultConsumer.accept(InstallStatus.INSTALLED, dependency);

            PackageInfo packageInfo = packageManager.readPackageInfo(packageInfoFile);
            return packageInfo.getDependencies();
        } finally {
            IOUtils.close(in);
        }
    }

}
