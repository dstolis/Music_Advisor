import advisor.Main;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.WebServerMock;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.AfterClass;

public class MusicAdvisorTest extends StageTest<String> {

    private static final String fictiveAuthCode = "123123";
    private static final String fictiveAccessToken = "456456";
    private static final String fictiveRefreshToken = "567567";


    private static final int accessServerPort = 45678;
    private static final int resourceServerPort = 56789;

    private static final String accessServerUrl = "http://127.0.0.1:" + accessServerPort;
    private static final String resourceServerUrl = "http://127.0.0.1:" + resourceServerPort;

    private static final String[] arguments = new String[]{
            "-access",
            accessServerUrl,
            "-resource",
            resourceServerUrl
    };

    private static final String tokenResponse = "{" +
            "\"access_token\":\"" + fictiveAccessToken + "\"," +
            "\"token_type\":\"Bearer\"," +
            "\"expires_in\":3600," +
            "\"refresh_token\":" + "\"" + fictiveRefreshToken + "\"," +
            "\"scope\":\"\"" +
            "}";

    // TODO handle auth code argument to get the token.
    private static WebServerMock accessServer = new WebServerMock(accessServerPort)
            .setPage("/api/token", tokenResponse);


    private static final String spotifyServerUrl = "https://api\\.spotify\\.com";

    private static final String apiCategoriesResponse = "{\n" +
            "    \"categories\": {\n" +
            "        \"href\": \"https://api.spotify.com/v1/browse/categories?offset=0&limit=20\",\n" +
            "        \"items\": [\n" +
            "            {\n" +
            "                \"href\": \"https://api.spotify.com/v1/browse/categories/toplists\",\n" +
            "                \"icons\": [\n" +
            "                    {\n" +
            "                        \"height\": 275,\n" +
            "                        \"url\": \"https://datsnxq1rwndn.cloudfront.net/media/derived/toplists_11160599e6a04ac5d6f2757f5511778f_0_0_275_275.jpg\",\n" +
            "                        \"width\": 275\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"id\": \"toplists\",\n" +
            "                \"name\": \"Top Lists\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"href\": \"https://api.spotify.com/v1/browse/categories/mood\",\n" +
            "                \"icons\": [\n" +
            "                    {\n" +
            "                        \"height\": 274,\n" +
            "                        \"url\": \"https://datsnxq1rwndn.cloudfront.net/media/original/mood-274x274_976986a31ac8c49794cbdc7246fd5ad7_274x274.jpg\",\n" +
            "                        \"width\": 274\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"id\": \"mood\",\n" +
            "                \"name\": \"Super Mood\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"href\": \"https://api.spotify.com/v1/browse/categories/party\",\n" +
            "                \"icons\": [\n" +
            "                    {\n" +
            "                        \"height\": 274,\n" +
            "                        \"url\": \"https://datsnxq1rwndn.cloudfront.net/media/derived/party-274x274_73d1907a7371c3bb96a288390a96ee27_0_0_274_274.jpg\",\n" +
            "                        \"width\": 274\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"id\": \"party\",\n" +
            "                \"name\": \"Party Time\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"limit\": 20,\n" +
            "        \"next\": null,\n" +
            "        \"offset\": 0,\n" +
            "        \"previous\": null,\n" +
            "        \"total\": 3\n" +
            "    }\n" +
            "}".replaceAll(spotifyServerUrl, resourceServerUrl);


    private static final String apiPlaylistsPartyResponse = "{\n" +
            "    \"playlists\": {\n" +
            "        \"href\": \"https://api.spotify.com/v1/browse/categories/party/playlists?offset=0&limit=20\",\n" +
            "        \"items\": [\n" +
            "            {\n" +
            "                \"collaborative\": false,\n" +
            "                \"external_urls\": {\n" +
            "                    \"spotify\": \"http://open.spotify.com/user/spotifybrazilian/playlist/4k7EZPI3uKMz4aRRrLVfen\"\n" +
            "                },\n" +
            "                \"href\": \"https://api.spotify.com/v1/users/spotifybrazilian/playlists/4k7EZPI3uKMz4aRRrLVfen\",\n" +
            "                \"id\": \"4k7EZPI3uKMz4aRRrLVfen\",\n" +
            "                \"images\": [\n" +
            "                    {\n" +
            "                        \"height\": 300,\n" +
            "                        \"url\": \"https://i.scdn.co/image/bf6544c213532e9650088dfef76c8521093d970e\",\n" +
            "                        \"width\": 300\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": \"Noite Eletronica\",\n" +
            "                \"owner\": {\n" +
            "                    \"external_urls\": {\n" +
            "                        \"spotify\": \"http://open.spotify.com/user/spotifybrazilian\"\n" +
            "                    },\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotifybrazilian\",\n" +
            "                    \"id\": \"spotifybrazilian\",\n" +
            "                    \"type\": \"user\",\n" +
            "                    \"uri\": \"spotify:user:spotifybrazilian\"\n" +
            "                },\n" +
            "                \"public\": null,\n" +
            "                \"snapshot_id\": \"PULvu1V2Ps8lzCxNXfNZTw4QbhBpaV0ZORc03Mw6oj6kQw9Ks2REwhL5Xcw/74wL\",\n" +
            "                \"tracks\": {\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotifybrazilian/playlists/4k7EZPI3uKMz4aRRrLVfen/tracks\",\n" +
            "                    \"total\": 100\n" +
            "                },\n" +
            "                \"type\": \"playlist\",\n" +
            "                \"uri\": \"spotify:user:spotifybrazilian:playlist:4k7EZPI3uKMz4aRRrLVfen\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"collaborative\": false,\n" +
            "                \"external_urls\": {\n" +
            "                    \"spotify\": \"http://open.spotify.com/user/spotifybrazilian/playlist/4HZh0C9y80GzHDbHZyX770\"\n" +
            "                },\n" +
            "                \"href\": \"https://api.spotify.com/v1/users/spotifybrazilian/playlists/4HZh0C9y80GzHDbHZyX770\",\n" +
            "                \"id\": \"4HZh0C9y80GzHDbHZyX770\",\n" +
            "                \"images\": [\n" +
            "                    {\n" +
            "                        \"height\": 300,\n" +
            "                        \"url\": \"https://i.scdn.co/image/be6c333146674440123073cb32c1c8b851e69023\",\n" +
            "                        \"width\": 300\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": \"Festa Indie\",\n" +
            "                \"owner\": {\n" +
            "                    \"external_urls\": {\n" +
            "                        \"spotify\": \"http://open.spotify.com/user/spotifybrazilian\"\n" +
            "                    },\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotifybrazilian\",\n" +
            "                    \"id\": \"spotifybrazilian\",\n" +
            "                    \"type\": \"user\",\n" +
            "                    \"uri\": \"spotify:user:spotifybrazilian\"\n" +
            "                },\n" +
            "                \"public\": null,\n" +
            "                \"snapshot_id\": \"V66hh9k2HnLCdzHvtoXPv+tm0jp3ODM63SZ0oISfGnlHQxwG/scupDbKgIo99Zfz\",\n" +
            "                \"tracks\": {\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotifybrazilian/playlists/4HZh0C9y80GzHDbHZyX770/tracks\",\n" +
            "                    \"total\": 74\n" +
            "                },\n" +
            "                \"type\": \"playlist\",\n" +
            "                \"uri\": \"spotify:user:spotifybrazilian:playlist:4HZh0C9y80GzHDbHZyX770\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"limit\": 20,\n" +
            "        \"next\": null,\n" +
            "        \"offset\": 0,\n" +
            "        \"previous\": null,\n" +
            "        \"total\": 2\n" +
            "    }\n" +
            "}".replaceAll(spotifyServerUrl, resourceServerUrl);

    private static final String testErrorMessage = "Test unpredictable error message";

    private static final String apiTestErrorResponse = "{\"error\":{\"status\":404,\"message\":\"" + testErrorMessage + "\"}}";

    private static final String apiNewReleasesResponse = "{\n" +
            "    \"albums\": {\n" +
            "        \"href\": \"https://api.spotify.com/v1/browse/new-releases?offset=0&limit=20\",\n" +
            "        \"items\": [\n" +
            "            {\n" +
            "                \"album_type\": \"single\",\n" +
            "                \"artists\": [\n" +
            "                    {\n" +
            "                        \"external_urls\": {\n" +
            "                            \"spotify\": \"https://open.spotify.com/artist/2RdwBSPQiwcmiDo9kixcl8\"\n" +
            "                        },\n" +
            "                        \"href\": \"https://api.spotify.com/v1/artists/2RdwBSPQiwcmiDo9kixcl8\",\n" +
            "                        \"id\": \"2RdwBSPQiwcmiDo9kixcl8\",\n" +
            "                        \"name\": \"Pharrell Williams\",\n" +
            "                        \"type\": \"artist\",\n" +
            "                        \"uri\": \"spotify:artist:2RdwBSPQiwcmiDo9kixcl8\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"available_markets\": [\n" +
            "                    \"AD\"\n" +
            "                ],\n" +
            "                \"external_urls\": {\n" +
            "                    \"spotify\": \"https://open.spotify.com/album/5ZX4m5aVSmWQ5iHAPQpT71\"\n" +
            "                },\n" +
            "                \"href\": \"https://api.spotify.com/v1/albums/5ZX4m5aVSmWQ5iHAPQpT71\",\n" +
            "                \"id\": \"5ZX4m5aVSmWQ5iHAPQpT71\",\n" +
            "                \"name\": \"Runnin'\",\n" +
            "                \"type\": \"album\",\n" +
            "                \"uri\": \"spotify:album:5ZX4m5aVSmWQ5iHAPQpT71\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"album_type\": \"single\",\n" +
            "                \"artists\": [\n" +
            "                    {\n" +
            "                        \"external_urls\": {\n" +
            "                            \"spotify\": \"https://open.spotify.com/artist/3TVXtAsR1Inumwj472S9r4\"\n" +
            "                        },\n" +
            "                        \"href\": \"https://api.spotify.com/v1/artists/3TVXtAsR1Inumwj472S9r4\",\n" +
            "                        \"id\": \"3TVXtAsR1Inumwj472S9r4\",\n" +
            "                        \"name\": \"Drake2\",\n" +
            "                        \"type\": \"artist\",\n" +
            "                        \"uri\": \"spotify:artist:3TVXtAsR1Inumwj472S9r4\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"external_urls\": {\n" +
            "                            \"spotify\": \"https://open.spotify.com/artist/3TVXtAsR1Inumwj472S9r4\"\n" +
            "                        },\n" +
            "                        \"href\": \"https://api.spotify.com/v1/artists/3TVXtAsR1Inumwj472S9r4\",\n" +
            "                        \"id\": \"3TVXtAsR1Inumwj472S9r4\",\n" +
            "                        \"name\": \"Drake3\",\n" +
            "                        \"type\": \"artist\",\n" +
            "                        \"uri\": \"spotify:artist:3TVXtAsR1Inumwj472S9r4\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"available_markets\": [\n" +
            "                    \"AD\"\n" +
            "                ],\n" +
            "                \"external_urls\": {\n" +
            "                    \"spotify\": \"https://open.spotify.com/album/0geTzdk2InlqIoB16fW9Nd\"\n" +
            "                },\n" +
            "                \"href\": \"https://api.spotify.com/v1/albums/0geTzdk2InlqIoB16fW9Nd\",\n" +
            "                \"id\": \"0geTzdk2InlqIoB16fW9Nd\",\n" +
            "                \"name\": \"Sneakin'\",\n" +
            "                \"type\": \"album\",\n" +
            "                \"uri\": \"spotify:album:0geTzdk2InlqIoB16fW9Nd\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"limit\": 20,\n" +
            "        \"next\": null,\n" +
            "        \"offset\": 0,\n" +
            "        \"previous\": null,\n" +
            "        \"total\": 2\n" +
            "    }\n" +
            "}".replaceAll(spotifyServerUrl, resourceServerUrl);


    private static final String apiFeaturedPlaylistsResponse = "{\n" +
            "    \"message\": \"Monday morning music, coming right up!\",\n" +
            "    \"playlists\": {\n" +
            "        \"href\": \"https://api.spotify.com/v1/browse/featured-playlists?offset=0&limit=20\",\n" +
            "        \"items\": [\n" +
            "            {\n" +
            "                \"collaborative\": false,\n" +
            "                \"external_urls\": {\n" +
            "                    \"spotify\": \"http://open.spotify.com/user/spotify/playlist/6ftJBzU2LLQcaKefMi7ee7\"\n" +
            "                },\n" +
            "                \"href\": \"https://api.spotify.com/v1/users/spotify/playlists/6ftJBzU2LLQcaKefMi7ee7\",\n" +
            "                \"id\": \"6ftJBzU2LLQcaKefMi7ee7\",\n" +
            "                \"images\": [\n" +
            "                    {\n" +
            "                        \"height\": 300,\n" +
            "                        \"url\": \"https://i.scdn.co/image/7bd33c65ebd1e45975bbcbbf513bafe272f033c7\",\n" +
            "                        \"width\": 300\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": \"Monday Morning Mood\",\n" +
            "                \"owner\": {\n" +
            "                    \"external_urls\": {\n" +
            "                        \"spotify\": \"http://open.spotify.com/user/spotify\"\n" +
            "                    },\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotify\",\n" +
            "                    \"id\": \"spotify\",\n" +
            "                    \"type\": \"user\",\n" +
            "                    \"uri\": \"spotify:user:spotify\"\n" +
            "                },\n" +
            "                \"public\": null,\n" +
            "                \"snapshot_id\": \"WwGvSIVUkUvGvqjgj/bQHlRycYmJ2TkoIxYfoalWlmIZT6TvsgvGMgtQ2dGbkrAW\",\n" +
            "                \"tracks\": {\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotify/playlists/6ftJBzU2LLQcaKefMi7ee7/tracks\",\n" +
            "                    \"total\": 245\n" +
            "                },\n" +
            "                \"type\": \"playlist\",\n" +
            "                \"uri\": \"spotify:user:spotify:playlist:6ftJBzU2LLQcaKefMi7ee7\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"collaborative\": false,\n" +
            "                \"external_urls\": {\n" +
            "                    \"spotify\": \"http://open.spotify.com/user/spotify__sverige/playlist/4uOEx4OUrkoGNZoIlWMUbO\"\n" +
            "                },\n" +
            "                \"href\": \"https://api.spotify.com/v1/users/spotify__sverige/playlists/4uOEx4OUrkoGNZoIlWMUbO\",\n" +
            "                \"id\": \"4uOEx4OUrkoGNZoIlWMUbO\",\n" +
            "                \"images\": [\n" +
            "                    {\n" +
            "                        \"height\": 300,\n" +
            "                        \"url\": \"https://i.scdn.co/image/24aa1d1b491dd529b9c03392f350740ed73438d8\",\n" +
            "                        \"width\": 300\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": \"Upp och hoppa!\",\n" +
            "                \"owner\": {\n" +
            "                    \"external_urls\": {\n" +
            "                        \"spotify\": \"http://open.spotify.com/user/spotify__sverige\"\n" +
            "                    },\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotify__sverige\",\n" +
            "                    \"id\": \"spotify__sverige\",\n" +
            "                    \"type\": \"user\",\n" +
            "                    \"uri\": \"spotify:user:spotify__sverige\"\n" +
            "                },\n" +
            "                \"public\": null,\n" +
            "                \"snapshot_id\": \"0j9Rcbt2KtCXEXKtKy/tnSL5r4byjDBOIVY1dn4S6GV73EEUgNuK2hU+QyDuNnXz\",\n" +
            "                \"tracks\": {\n" +
            "                    \"href\": \"https://api.spotify.com/v1/users/spotify__sverige/playlists/4uOEx4OUrkoGNZoIlWMUbO/tracks\",\n" +
            "                    \"total\": 38\n" +
            "                },\n" +
            "                \"type\": \"playlist\",\n" +
            "                \"uri\": \"spotify:user:spotify__sverige:playlist:4uOEx4OUrkoGNZoIlWMUbO\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"limit\": 20,\n" +
            "        \"next\": null,\n" +
            "        \"offset\": 0,\n" +
            "        \"previous\": null,\n" +
            "        \"total\": 2\n" +
            "    }\n" +
            "}".replaceAll(spotifyServerUrl, resourceServerUrl);


    private static final WebServerMock resourceServerMock = new WebServerMock(resourceServerPort)
            .setPage("/v1/browse/categories", apiCategoriesResponse)
            .setPage("/v1/browse/categories/party/playlists", apiPlaylistsPartyResponse)
            // unpredictable error on toplists request!!!
            .setPage("/v1/browse/categories/toplists/playlists", apiTestErrorResponse)
            .setPage("/v1/browse/new-releases", apiNewReleasesResponse)
            .setPage("/v1/browse/featured-playlists", apiFeaturedPlaylistsResponse);

    private static final MockTokenServer tokenServer = new MockTokenServer(accessServer);
    private static final MockTokenServer resourceServer = new MockTokenServer(resourceServerMock);

    public static void auth(TestedProgram userProgram) {

        Server server = new Server(userProgram, fictiveAuthCode);
        server.start();
        if (!accessServer.isStarted())
            tokenServer.start();

        if (!resourceServerMock.isStarted())
            resourceServer.start();

        userProgram.goBackground();
        userProgram.execute("auth");

        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Server.checkResult != null) {
            throw new WrongAnswer(Server.checkResult.getFeedback());
        }

        userProgram.stopBackground();
    }

    @DynamicTestingMethod
    CheckResult testNewWithoutAuth() {

        TestedProgram userProgram = new TestedProgram(Main.class);
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        userProgram.execute("new");
        String outputAfterNew = userProgram.getOutput();

        if (!outputAfterNew.strip().startsWith("Please, provide access for application.")) {
            return CheckResult.wrong("When no access provided you should output " +
                    "\"Please, provide access for application.\"");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testFeaturedWithoutAuth() {

        TestedProgram userProgram = new TestedProgram(Main.class);
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        userProgram.execute("featured");
        String outputAfterNew = userProgram.getOutput();

        if (!outputAfterNew.strip().startsWith("Please, provide access for application.")) {
            return CheckResult.wrong("When no access provided you should output " +
                    "\"Please, provide access for application.\"");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testCategoriesWithoutAuth() {

        TestedProgram userProgram = new TestedProgram(Main.class);
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        userProgram.execute("categories");
        String outputAfterNew = userProgram.getOutput();

        if (!outputAfterNew.strip().startsWith("Please, provide access for application.")) {
            return CheckResult.wrong("When no access provided you should output " +
                    "\"Please, provide access for application.\"");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testPlaylistWithoutAuth() {

        TestedProgram userProgram = new TestedProgram(Main.class);
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        userProgram.execute("playlists Party Time");
        String outputAfterNew = userProgram.getOutput();

        if (!outputAfterNew.strip().startsWith("Please, provide access for application.")) {
            return CheckResult.wrong("When no access provided you should output " +
                    "\"Please, provide access for application.\"");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testAuth() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();

    }

    @DynamicTestingMethod
    CheckResult testNew() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("new");

        String outputAfterNew = userProgram.getOutput();

        String album1 =
                "Runnin'\n" +
                        "[Pharrell Williams]\n" +
                        "https://open.spotify.com/album/5ZX4m5aVSmWQ5iHAPQpT71"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        String album2 =
                "Sneakin'\n" +
                        "[Drake2, Drake3]\n" +
                        "https://open.spotify.com/album/0geTzdk2InlqIoB16fW9Nd"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        if (outputAfterNew.contains("Invalid access token")) {
            return CheckResult.wrong("Your answer was `Invalid access token` on `new` action. " +
                    "Make sure you use the server from -resource command line argument.");
        }

        if (!outputAfterNew.contains(album1) || !outputAfterNew.contains(album2)) {
            return CheckResult.wrong(
                    "There are no albums in correct format on \"new\" action. " +
                            "Make sure you use the server from -resource command line argument.");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testCategories() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("categories");

        String outputAfterCategories = userProgram.getOutput();

        String category1 = "Top Lists";
        String category2 = "Super Mood";
        String category3 = "Party Time";

        if (!outputAfterCategories.contains(category1)
                || !outputAfterCategories.contains(category2)
                || !outputAfterCategories.contains(category3)) {

            return CheckResult.wrong("There are no categories in correct format on \"category\" action");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testFeatured() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("featured");

        String outputAfterFeatured = userProgram.getOutput();

        String featured1 =
                "Monday Morning Mood\n" +
                        "http://open.spotify.com/user/spotify/playlist/6ftJBzU2LLQcaKefMi7ee7"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        String featured2 =
                "Upp och hoppa!\n" +
                        "http://open.spotify.com/user/spotify__sverige/playlist/4uOEx4OUrkoGNZoIlWMUbO"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        if (!outputAfterFeatured.contains(featured1)
                || !outputAfterFeatured.contains(featured2)) {

            return CheckResult.wrong("There are no featured playlists in correct format on \"featured\" action");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testPartyPlayList() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("playlists Party Time");

        String outputAfterPartyPlaylist = userProgram.getOutput();

        String playlist1 =
                "Noite Eletronica\n" +
                        "http://open.spotify.com/user/spotifybrazilian/playlist/4k7EZPI3uKMz4aRRrLVfen"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        String playlist2 =
                "Festa Indie\n" +
                        "http://open.spotify.com/user/spotifybrazilian/playlist/4HZh0C9y80GzHDbHZyX770"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        if (!outputAfterPartyPlaylist.contains(playlist1)
                || !outputAfterPartyPlaylist.contains(playlist2)) {
            return CheckResult.wrong("There are no playlists in correct format on \"playlists {name}\" action. " +
                    "Make sure you correctly parsed the category name.");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testUnknownPlayList() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("playlists Party Time");

        String outputAfterUnknownPlaylist = userProgram.getOutput();

        String playlist1 =
                "Noite Eletronica\n" +
                        "http://open.spotify.com/user/spotifybrazilian/playlist/4k7EZPI3uKMz4aRRrLVfen"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        String playlist2 =
                "Festa Indie\n" +
                        "http://open.spotify.com/user/spotifybrazilian/playlist/4HZh0C9y80GzHDbHZyX770"
                                .replaceAll(spotifyServerUrl, resourceServerUrl);

        if (!outputAfterUnknownPlaylist.contains(playlist1)
                || !outputAfterUnknownPlaylist.contains(playlist2)) {

            return CheckResult.wrong("There are no playlists in correct format on \"playlists {name}\" action. " +
                            "Make sure you correctly parsed the category name.");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testTopPlayList() {

        TestedProgram userProgram = new TestedProgram(Main.class);

        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        auth(userProgram);

        userProgram.execute("playlists Top Lists");

        String outputAfterUnknownPlaylist = userProgram.getOutput();

        if (!outputAfterUnknownPlaylist.contains(testErrorMessage)) {
            return new CheckResult(false,
                    "You got a json with unpredictable error from the api. " +
                            "Error message should be parsed from the api response and printed.");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @AfterClass
    public static void afterTest() {
        tokenServer.stopMock();
        resourceServer.stopMock();
    }

}