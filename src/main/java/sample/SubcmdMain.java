package sample;

import static util.Utils.putsf;
import static util.Utils.putskv;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import sample.model.Model;

public class SubcmdMain {

    public static void main(String[] rawArgs) throws Exception {
        for (int i = 0; i < rawArgs.length; i++) {
            putsf("raw arg %s (%s)", i, rawArgs[i]);
        }

        String currentDir = rawArgs[0];
        String projectDir = rawArgs[1];
        String subcmd = rawArgs[2];
        putskv("currentDir", currentDir);
        putskv("projectDir", projectDir);
        putskv("subcmd", subcmd);

        String[] mainArgs = new String[rawArgs.length - 3];
        for (int i = 3; i < rawArgs.length; i++) {
            mainArgs[i - 3] = rawArgs[i];
        }

        Options opts = new Options();
        opts.addOption("h", "help", false, "Print help");

        CommandLineParser parser = new DefaultParser();
        CommandLine cl;

        switch (subcmd) {
        case "cmd_a":
            opts.addOption("f", "foo", false, "Option foo");
            cl = parser.parse(opts, mainArgs);

            checkHelp(cl, opts);
            setEnv(cl);
            Config.load();

            Model.cmdA(cl.hasOption("f"), cl.getArgList());
            break;

        case "cmd_b":
            opts.addOption("b", "bar", true, "Option bar");
            cl = parser.parse(opts, mainArgs);

            checkHelp(cl, opts);
            setEnv(cl);
            Config.load();

            Model.cmdB(cl.getOptionValue("b"), cl.getArgList());
            break;

        default:
            throw new IllegalAccessException("cmd (" + subcmd + ")");
        }
    }

    private static void checkHelp(CommandLine cl, Options opts) {
        if (cl.hasOption("help")) {
            new HelpFormatter().printHelp("run_subcmd_(mvn|gradle).sh", opts);
            System.exit(0);
        }
    }

    private static void setEnv(CommandLine cl) {
        if (cl.hasOption("env")) {
            Config.setEnv(cl.getOptionValue("env"));
        } else {
            Config.setEnv("devel");
        }

    }
}
