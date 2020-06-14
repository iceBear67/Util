package cc.sfclub.util.common;

import cc.sfclub.util.Util;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateChecker {
    private final String projectName;
    private final String projectOwner;
    private final Pattern branch;

    public UpdateChecker(String projectOwner, String project, String branch) {
        this.projectOwner = projectOwner;
        this.projectName = project;
        this.branch = Pattern.compile(branch);
    }

    public Optional<ReleaseInfo> check() {
        ReleaseInfo releaseInfo = Util.getGson().fromJson(Http.get("https://api.github.com/repos/" + projectOwner + "/" + projectName + "/releases/latest"), ReleaseInfo.class);
        if (releaseInfo == null || releaseInfo.branch == null) return Optional.empty();
        if (branch.matcher(releaseInfo.branch).find()) return Optional.of(releaseInfo);
        return Optional.empty();
    }

    @Data
    public static class ReleaseInfo {
        public String tag_name;
        @SerializedName("tag_commitish")
        public String branch;
        public String body;
        public String published_at;
    }
}
