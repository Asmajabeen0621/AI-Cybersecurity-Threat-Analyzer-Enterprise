package com.cyberai.service;

import org.springframework.stereotype.Service;

import org.tribuo.Feature;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.impl.ArrayExample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MlThreatClassifier {

    private Model<Label> model;

    private final Path modelPath =
            Paths.get("data/ml-threat-model.pb");

    /**
     * Train the cybersecurity threat classification model.
     */
    public synchronized void train() throws Exception {

        Path csv =
                Paths.get("src/main/resources/ml/training.csv");

        LabelFactory labelFactory =
                new LabelFactory();

        CSVLoader<Label> csvLoader =
                new CSVLoader<>(labelFactory);

        // "label" is the target/output column
        var source =
                csvLoader.loadDataSource(csv, "label");

        MutableDataset<Label> data =
                new MutableDataset<>(source);

        LogisticRegressionTrainer trainer =
                new LogisticRegressionTrainer();

        model = trainer.train(data);

        Files.createDirectories(modelPath.getParent());

        model.serializeToFile(modelPath);
    }

    /**
     * Predict the threat severity.
     */
    public synchronized String predict(
            double failedAttempts,
            double phishingScore,
            double malwareScore,
            double intelMatch) throws Exception {

        if (model == null) {

            if (Files.exists(modelPath)) {

                Model<?> loadedModel =
                        Model.deserializeFromFile(modelPath);

                model =
                        loadedModel.castModel(Label.class);

            } else {

                train();
            }
        }

        ArrayExample<Label> example =
                new ArrayExample<>(new Label("LOW"));

        example.add(
                new Feature(
                        "failedAttempts",
                        failedAttempts));

        example.add(
                new Feature(
                        "phishingScore",
                        phishingScore));

        example.add(
                new Feature(
                        "malwareScore",
                        malwareScore));

        example.add(
                new Feature(
                        "intelMatch",
                        intelMatch));

        Label prediction =
                model.predict(example).getOutput();

        return prediction.getLabel();
    }
}