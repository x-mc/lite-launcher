package net.minestudio.launcher.version;


import com.google.gson.Gson;
import net.minestudio.launcher.http.Response;
import net.minestudio.launcher.http.Webb;

public final class VersionService {
    private static final Gson GSON = new Gson();

    private static final String VERSION_API_ENDPOINT = "https://versions.mysterymod.net/api/v1/lightweight/version/select";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0";
    private static final String CONTENT = "application/json";

    public static VersionInfoResponse select(SelectVersionRequest request) {
        Response<VersionInfoResponse> jsonObjectResponse = Webb.create().post(VERSION_API_ENDPOINT)
                .header("User-Agent", USER_AGENT)
                .header("Content-Type", CONTENT)
                .header("Accept", CONTENT)
                .body(GSON.toJson(request))
                .toClass(VersionInfoResponse.class);
        return jsonObjectResponse.getBody();
    }
}
