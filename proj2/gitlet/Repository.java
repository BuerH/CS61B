package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File BLOB_DIR = join(GITLET_DIR, "blob");

    public static final File BLOB_ALL = join(BLOB_DIR, "ALL");

    public static final File HEADS_DIR = join(GITLET_DIR, "heads");

    public static final File STAGE_DIR = join(GITLET_DIR, "stage");

    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /* TODO: fill in the rest of this class. */

    /**
     * Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit: a commit that contains
     * no files and has the commit message initial commit (just like that, with no punctuation).
     * It will have a single branch: master, which initially points to this initial commit,
     * and master will be the current branch. The timestamp for this initial commit will be 00:00:00 UTC,
     * Thursday, 1 January 1970 in whatever format you choose for dates (this is called “The (Unix) Epoch”,
     * represented internally by the time 0.)
     * Since the initial commit in all repositories created by Gitlet will have exactly the same content,
     * it follows that all repositories will automatically share this commit
     * (they will all have the same UID) and all commits in all repositories will trace back to it.
     */
    public void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        } else {
            Commit init = new Commit("initial commit", "master", "00:00:00 UTC, Thursday, 1 January 1970", null);
            GITLET_DIR.mkdir();
            BLOB_DIR.mkdir();
            HEADS_DIR.mkdir();
            STAGE_DIR.mkdir();
            //获取对象的hashcode作为文件名称，这里不用检查文件内容，所以使用hashcode
            String uni_code = Integer.toHexString(init.hashCode());

            //
            File commit_dir_sha = join(BLOB_DIR, uni_code.substring(0, 2));
            commit_dir_sha.mkdir();

            File master = join(HEADS_DIR, "master");

            File content = Utils.join(commit_dir_sha, uni_code);
            Utils.writeObject(content, init);
            Utils.writeContents(HEAD, uni_code);
            Utils.writeContents(master, uni_code);
        }
    }
    /**
     * Adds a copy of the file as it currently exists to the staging area
     * (see the description of the commit command).
     * For this reason, adding a file is also called staging the file for addition.
     * Staging an already-staged file overwrites the previous entry in the staging
     * area with the new contents. The staging area should be somewhere in .gitlet.
     * If the current working version of the file is identical to the version in the current commit,
     * do not stage it to be added, and remove it from the staging area if it is already there
     * (as can happen when a file is changed, added, and then changed back to it’s original version).
     * The file will no longer be staged for removal (see gitlet rm),
     * if it was at the time of the command.
     */
    public void add(String[] args) {
        warnNotInit();
        if (args.length < 2) {
            System.out.println("File does not exist.");
        }
        //文件名
        String fileName = args[1];
        //判断文件是否存在
        File f = Utils.join(CWD, fileName);
        if (!f.exists()) {
            System.out.println("File does not exist.");
        } else { //文件存在
            //获取文件的内容
            String contents = Utils.readContentsAsString(f);
            //获取文件的sha1值
            String con_sha1 = Utils.sha1(contents);

            //和已经commit的内容进行对比
            //读取当前头指针对应的文件名
            String uni_code = readContentsAsString(HEAD);
            //得到文件名所在的地址
            File blob = join(BLOB_DIR, uni_code.substring(0, 2), uni_code);
            //得到head指向的commit的内容
            Commit head = readObject(blob, Commit.class);

            //判断上一次提交是否存在当前文件
            if (head.getMap() == null) {//是否问init的状态
                createStage(fileName, con_sha1, contents);
            } else {//上一次commit的所有文件不为空
                HashMap<String, String> map = head.getMap();
                if (!con_sha1.equals(map.get(fileName))) {//判断之前提交的文件的sha1值与当前的是否相等
                    createStage(fileName, con_sha1, contents);
                }
            }
        }
    }

    /**
     * 创建暂存区
     * @param fileName 文件名
     * @param con_sha1 文件内容的sha1值
     * @param contents 文件的内容
     */
    public void createStage(String fileName, String con_sha1, String contents) {
        //找到暂存区的文件
        File stage = Utils.join(STAGE_DIR, "stage");
        HashMap<String, String> stageMap;
        //判断暂存区是否为空
        if (stage.exists()) {//暂存区不为空
            //读取暂存区的文件
            stageMap = readObject(stage, HashMap.class);
            //获取暂存区里的文件的sha1值
            String file_sha1 = stageMap.get(fileName);
            if (!con_sha1.equals(file_sha1)) {//看问价是否有改动
                //改动了就重新更新暂存区的内容
                stageMap.put(fileName, con_sha1);
            }
        } else {
            //暂存区不存在内容
            stageMap = new HashMap<>();
            stageMap.put(fileName, con_sha1);
        }
        //向暂存区写入内容
        Utils.writeObject(stage, stageMap);

        //blob的集合
        HashSet<String> blobSet;
        if (BLOB_ALL.exists()) {//blob集合是否存在
            //读取含有全部blob的集合
            blobSet = Utils.readObject(BLOB_ALL, HashSet.class);
            //判断集合中是否由当前文件
            if (!blobSet.contains(con_sha1)) {
                //保存文件内容到名字为文件内容得到的sha1值命名的文件中
                saveBlob(con_sha1, contents);
                //集合中添加新的blob
                blobSet.add(con_sha1);
                //将新的blob集合写入文件
                Utils.writeObject(BLOB_ALL, blobSet);
            }
        } else {
            //新建blob集合
            blobSet = new HashSet<>();
            //集合中添加新的blob
            blobSet.add((con_sha1));
            //保存文件内容到名字为文件内容得到的sha1值命名的文件中
            saveBlob(con_sha1, contents);
            //将新的blob集合写入文件
            Utils.writeObject(BLOB_ALL, blobSet);
        }
    }

    /**
     * 保存文件到git中
     * @param con_sha1
     * @param contents
     */
    public void saveBlob(String con_sha1, String contents) {
        //判断文件的sha1值前两个字符创建的文件夹是否存在
        File curBlobDir = join(BLOB_DIR, con_sha1.substring(0, 2));
        if (!curBlobDir.exists()) {
            curBlobDir.mkdir();
        }
        //将blob写入文件
        File curBlob = join(BLOB_DIR, con_sha1.substring(0, 2), con_sha1);
        Utils.writeContents(curBlob, contents);
    }

    /**
     * 判断是否gitlet化
     */
    public void warnNotInit() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not init");
            System.exit(0);
        }
    }

    public void commit(String message) {

    }
}
