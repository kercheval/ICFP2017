package com.godaddy.icfp2017;

import com.godaddy.icfp2017.services.GameDriver;
import com.godaddy.icfp2017.services.GameLogic;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;

class Application {

  public static void main(String[] args) throws Exception {
    OptionParser parser = new OptionParser();

    parser.accepts("mode")
          .withRequiredArg().ofType(String.class)
          .defaultsTo("offline")
          .describedAs("offline or online");

    parser.accepts("host")
          .withOptionalArg().ofType(String.class)
          .describedAs("online server hostname or ip address");

    parser.accepts("port")
          .withOptionalArg().ofType(Integer.class)
          .describedAs("online server ip address");

    parser.accepts("debug");

    parser.accepts("capture");

    parser.accepts("?")
          .forHelp();

    OptionSet options = parser.parse(args);

    if (options.has("?")) {
      parser.printHelpOn(System.out);
      return;
    }

    GameLogic gameLogic = new GameLogic();


    GameDriver gameDriver = null;
    Socket communicationSocket = null;

    try {
      OutputStream debugStream = null;

      if (options.has("debug")) {
        debugStream = System.err;
      }

      final boolean shouldCapture = options.has("capture");

      if (options.valueOf("mode").equals("offline")) {
        gameDriver = new GameDriver(System.in, System.out, debugStream, gameLogic, shouldCapture);
      }

      if (options.valueOf("mode").equals("online")) {
        final String host = (String) options.valueOf("host");
        final Integer port = (Integer) options.valueOf("port");
        communicationSocket = new Socket(host, port);
        communicationSocket.setTcpNoDelay(true);
        gameDriver = new GameDriver(new BufferedInputStream(communicationSocket.getInputStream()),
                                    communicationSocket.getOutputStream(),
                                    debugStream,
                                    gameLogic, shouldCapture);
      }

      assert gameDriver != null;
      gameDriver.run();

      if(shouldCapture) {
        gameDriver.dumpCapture(System.err);
      }

    }
    finally {
      if (gameDriver != null) {
        gameDriver.close();
      }
      if (communicationSocket != null) {
        communicationSocket.close();
      }
    }

  }
}
