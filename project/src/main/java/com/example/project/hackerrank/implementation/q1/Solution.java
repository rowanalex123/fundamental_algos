package com.example.project.hackerrank.implementation.q1;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Solution {

    private void run() throws Exception {
        int tests = in.nextInt();
        forn(tests, notUsed -> {
            long n = in.nextLong(), sweets = in.nextLong(), start = in.nextLong();
            op.out("{}", (((start - 1) + (sweets - 1)) % n) + 1);
        });
    }










    private final OutputHelper op = new OutputHelper();
    static final boolean devMode = "bsrinivasaraghav".equalsIgnoreCase(System.getenv("DEFAULT_USER"));
    static final String basePath = Solution.class.getResource(".").getPath() + File.separator;
    static final Scanner in = new Scanner(new BufferedInputStream(devMode ? executeQuitely(() -> new FileInputStream(basePath + "input.txt")) : System.in), "ISO-8859-1");

    final static void forn(int len, IntConsumer oper) {
        IntStream.range(0, len).forEach(oper);
    }

    final static void forn(int lenX, int lenY, BiConsumer<Integer, Integer> oper) {
        IntStream.range(0, lenY)
                .mapToObj(y -> y)
                .flatMap(y -> IntStream.range(0, lenX).mapToObj(x -> new Integer[]{x, y}))
                .forEach(pos -> oper.accept(pos[0], pos[1]));
    }








    /**
     * start helpers
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Solution instance = new Solution();
        instance.run();
        instance.op.endJob();
    }


    /**
     * exception helpers
     */
    public static <T> T executeQuitely(CheckedSupplier<T> op) {
        T retVal = null;
        try {
            retVal = op.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public static void executeQuitely(CheckedOperation op) {
        executeQuitely(() -> {
            op.run();
            return null;
        });
    }

    interface CheckedSupplier<T> {
        T get() throws Exception;
    }

    interface CheckedOperation {
        void run() throws Exception;
    }

    /**
     * output & log helper
     */
    class OutputHelper {


        private Writer outputFile = devMode ? executeQuitely(() -> new BufferedWriter(new FileWriter(basePath + "output.txt"))) : null;
        private long startTime = System.currentTimeMillis();

        public void log(String template, Object... args) {
            if (!devMode) return;
            FormattingTuple formattingTuple = new MessageFormatter().arrayFormat(template, args);
            String msg = formattingTuple.toString();
            System.out.println(msg);
        }

        public void out(String template, Object... args) {
            FormattingTuple formattingTuple = new MessageFormatter().arrayFormat(template, args);
            String msg = formattingTuple.toString() + "\n";
            System.out.print(msg);
            if (!devMode) return;
            executeQuitely(() -> outputFile.write(msg));
        }

        public boolean endJob() {
            if (!devMode) {
                System.out.flush();
                return true;
            }
            long endTime = System.currentTimeMillis();
            log("\n\nResult analysis\n---------------");
            log("Time taken: {}s", (endTime - startTime) / 1000);
            return executeQuitely(() -> {
                outputFile.close();
                BufferedReader expected = new BufferedReader(new FileReader(basePath + "expected.txt"));
                BufferedReader actual = new BufferedReader(new FileReader(basePath + "output.txt"));
                if (expected == null || actual == null) return true;
                StringBuilder op = new StringBuilder();
                String expectedStr, actualStr;
                boolean matchOk = true;
                while ((expectedStr = expected.readLine()) != null | (actualStr = actual.readLine()) != null) {
                    matchOk = matchOk && String.valueOf(expectedStr).equals(actualStr);
                    op.append(actualStr == null ? " " : actualStr);
                    op.append("\t|\t");
                    op.append(expectedStr == null ? " " : expectedStr);
                    op.append("\n");

                }
                log("Actual vs expected:\n{}", op.toString());
                log("\n{}\n", matchOk ? "~~~ Correct ~~~" : "### Incorrect ###");
                return matchOk;
            });
        }

        private class FormattingTuple {
            private String message;
            private Throwable throwable;

            public FormattingTuple(String message) {
                this(message, null, null);
            }

            public FormattingTuple(String message, Object[] argArray, Throwable throwable) {
                this.message = message;
                this.throwable = throwable;
            }

            public String toString() {
                return message + (throwable == null ? "" : (" excep: " + throwable));
            }
        }


        private final class MessageFormatter {
            static final String DELIM_STR = "{}";

            final Throwable getThrowableCandidate(Object[] argArray) {
                if (argArray == null || argArray.length == 0) {
                    return null;
                }

                final Object lastEntry = argArray[argArray.length - 1];
                if (lastEntry instanceof Throwable) {
                    return (Throwable) lastEntry;
                }
                return null;
            }

            final public FormattingTuple arrayFormat(final String messagePattern, final Object[] argArray) {
                Throwable throwableCandidate = getThrowableCandidate(argArray);
                Object[] args = argArray;
                if (throwableCandidate != null) {
                    args = trimmedCopy(argArray);
                }
                return arrayFormat(messagePattern, args, throwableCandidate);
            }

            private Object[] trimmedCopy(Object[] argArray) {
                if (argArray == null || argArray.length == 0) {
                    throw new IllegalStateException("non-sensical empty or null argument array");
                }
                final int trimemdLen = argArray.length - 1;
                Object[] trimmed = new Object[trimemdLen];
                System.arraycopy(argArray, 0, trimmed, 0, trimemdLen);
                return trimmed;
            }

            final public FormattingTuple arrayFormat(final String messagePattern, final Object[] argArray, Throwable throwable) {

                if (messagePattern == null) {
                    return new FormattingTuple(null, argArray, throwable);
                }

                if (argArray == null) {
                    return new FormattingTuple(messagePattern);
                }

                int i = 0;
                int j;
                // use string builder for better multicore performance
                StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);

                int L;
                for (L = 0; L < argArray.length; L++) {

                    j = messagePattern.indexOf(DELIM_STR, i);

                    if (j == -1) {
                        // no more variables
                        if (i == 0) { // this is a simple string
                            return new FormattingTuple(messagePattern, argArray, throwable);
                        } else { // add the tail string which contains no variables and return
                            // the result.
                            sbuf.append(messagePattern, i, messagePattern.length());
                            return new FormattingTuple(sbuf.toString(), argArray, throwable);
                        }
                    } else {
                        // normal case
                        sbuf.append(messagePattern, i, j);
                        deeplyAppendParameter(sbuf, argArray[L], new HashMap<Object[], Object>());
                        i = j + 2;
                    }
                }
                // append the characters following the last {} pair.
                sbuf.append(messagePattern, i, messagePattern.length());
                return new FormattingTuple(sbuf.toString(), argArray, throwable);
            }

            private void deeplyAppendParameter(StringBuilder sbuf, Object o, Map<Object[], Object> seenMap) {
                if (o == null) {
                    sbuf.append("null");
                    return;
                }
                if (!o.getClass().isArray()) {
                    sbuf.append(o);
                    return;
                }
                //accounts for primitive[], object [] & nested arrays of both types
                String deepToString = Arrays.deepToString(Arrays.asList(o).toArray());
                deepToString = deepToString.replace("], ", "]\n").replace("[[", "[").replace("]]", "]");
                sbuf.append("\n");
                sbuf.append(deepToString.substring(1, deepToString.length() - 1));
            }
        }
    }
}